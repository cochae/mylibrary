from flask import Flask, request, jsonify
import mysql.connector
import requests
from keybert import KeyBERT
from sentence_transformers import SentenceTransformer, util

bookrecommend = Flask(__name__)

keyword_model = KeyBERT('sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2')
embedding_model = SentenceTransformer('sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2')

#0511 수정 새로고침 반영 안 됨 문제 해결
import mysql.connector

def get_connection():
    return mysql.connector.connect(
        host="chaewonlibrary.mysql.database.azure.com",         # 로컬 MySQL 서버 주소
        user="ycw1203",              # 로컬 MySQL 사용자 이름 (원하는 걸로 바꿔주세요)
        password="chaewon1203!",              # 비밀번호가 있으면 입력
        database="library"        # 로컬에 만든 DB 이름
        # ssl_ca 제거: 로컬에서는 SSL 인증서 필요 없음
    )

API_KEY = 'ttbycw12031805001'

# top3카테고리 뽑는 함수 정의
def get_top_categories():
    #db연결 함수 추가 수정 0511
    conn = get_connection()
    cursor = conn.cursor(dictionary=True)
    query = """
        SELECT category, tag, COUNT(*) AS count
        FROM book
        GROUP BY category, tag
        ORDER BY count DESC
        LIMIT 3;
    """
    cursor.execute(query)
    results = cursor.fetchall()
    cursor.close()
    conn.close()
    return [(row['category'], row['tag']) for row in results]

def get_top_books(category_id, query_type='Bestseller'):
    url = "https://www.aladin.co.kr/ttb/api/ItemList.aspx"
    params = {
        'ttbkey': API_KEY,
        'QueryType': query_type,
        'MaxResults': 50,
        'start': 1,
        'SearchTarget': 'Book',
        'output': 'js',
        'CategoryId': category_id,
        'Version': '20131101',
        'cover': 'big'
    }
    #리스트 컴프리헨션 0512
    res = requests.get(url, params=params)
    data = res.json()
    return [ 
        {
            'title': item['title'],
            'author': item.get('author', ''),
            'description': item.get('description', ''),
            'img': item.get('cover', '')
        }
        for item in data.get('item', [])
    ]
def get_books_by_keyword(keyword):
    url = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx"
    params = {
        'ttbkey': API_KEY,
        'Query': keyword,
        'QueryType': 'Keyword',
        'MaxResults': 1,
        'start': 1,
        'SearchTarget': 'Book',
        'output': 'js',
        'Version': '20131101',
        'cover': 'big'
    }
    res = requests.get(url, params=params)
    data = res.json()
    return [{
        'title': item['title'],
        'author': item.get('author', ''),
        'description': item.get('description', ''),
        'img': item.get('cover', '')
    } for item in data.get('item', [])]

#해당 카테고리에 들어있는 책 제목, 설명 가져오는 함수 0512
def get_books_in_category(category_id):
    conn = get_connection()
    cursor = conn.cursor(dictionary=True)
    query = """
        SELECT title, description
        FROM book
        WHERE category = %s AND description IS NOT NULL AND description != ''
    """
    cursor.execute(query, (category_id,))
    results = cursor.fetchall()
    cursor.close()
    return results

#책에서 키워드 추출하는 함수
def extract_keywords_from_books(books):
    keyword_list = []
    for book in books:
        desc = book['description']
        if desc.strip():
            keywords = keyword_model.extract_keywords(desc, keyphrase_ngram_range=(1, 2), top_n=3)
            keyword_list.extend([kw[0] for kw in keywords])
    return list(set(keyword_list))

#0511책1:1 대응하는 추천 위해 추가
def extract_top_keywords_per_book(books):
    keywords = []
    for book in books:
        desc = book['description']
        if desc.strip():
            kw = keyword_model.extract_keywords(desc, keyphrase_ngram_range=(1, 1), top_n=1)
            if kw:
                keywords.append(kw[0][0])
    return keywords

def recommend_books_by_similarity(books, my_keywords, top_n=3):
    filtered_books = [b for b in books if b['description'].strip()]
    if not filtered_books:
        return []
    book_embeddings = embedding_model.encode([b['description'] for b in filtered_books], convert_to_tensor=True)
    keyword_embeddings = embedding_model.encode(my_keywords, convert_to_tensor=True)

    scores = util.pytorch_cos_sim(book_embeddings, keyword_embeddings).max(dim=1).values
    top_indices = scores.topk(top_n).indices.tolist()

    return [filtered_books[i] for i in top_indices]

@bookrecommend.route('/recommend', methods=['GET', 'POST'])
def recommend():
    top_categories = get_top_categories()
    all_recommendations = []
    
    for category_id, tag in top_categories:
        my_books = get_books_in_category(category_id)
        if not my_books:
            continue

        my_keywords = extract_keywords_from_books(my_books)
        if not my_keywords:
            continue

        my_titles = set(book['title'] for book in my_books)
        #베스트셀러 50개
        bestseller = get_top_books(category_id, query_type='Bestseller')
        #신간베스트 50개
        new_books = get_top_books(category_id, query_type='ItemNewSpecial')
        #0511수정 중복 없애기 위해 제목 필터링
        my_titles_set = set(my_titles)  # 리스트를 집합으로 변환
        #리스트를 집합으로 바꾸어 성능 향상
        bestseller_filtered = [book for book in bestseller if book['title'] not in my_titles]
        new_books_filtered = [book for book in new_books if book['title'] not in my_titles]
        #베스트셀러와 서재에 있는
        top_best = recommend_books_by_similarity(bestseller_filtered, my_keywords, top_n=3)
        top_new = recommend_books_by_similarity(new_books_filtered, my_keywords, top_n=3)

        keyword_list = extract_top_keywords_per_book(my_books)
        keyword_books = []
        for keyword in keyword_list:
            keyword_books.extend(get_books_by_keyword(keyword))
        keyword_recommendations = recommend_books_by_similarity(keyword_books, my_keywords, top_n=3)

        all_recommendations.append({
            "tag": tag,
            "category": category_id,
            "best": top_best,
            "new": top_new,
            "keyword": keyword_recommendations
        })

    return jsonify(all_recommendations)

if __name__ == '__main__':
    bookrecommend.run(port=5000)
