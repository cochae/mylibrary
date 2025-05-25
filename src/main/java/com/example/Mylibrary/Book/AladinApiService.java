package com.example.Mylibrary.Book;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AladinApiService {

    @Value("${aladin.api.key}")
    private String apiKey;

    public List<Book> searchBooks(String query, int page) {
        String url = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx"
                + "?ttbkey=" + apiKey
                + "&Query=" + query
                + "&QueryType=Keyword"
                + "&MaxResults=20"
                + "&start=" + page
                + "&SearchTarget=Book"
                + "&output=js"
                + "&Version=20131101"
                + "&cover=" + "big";  // cover 크기 추가


        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.optJSONArray("item");

            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);

                    String title = item.optString("title", "제목 없음");
                    String author = item.optString("author", "저자 없음");
                    String pubDate = item.optString("pubDate", "출판일 없음");
                    String description = item.optString("description", "설명 없음");
                    String imageUrl = item.optString("cover", null); // 표지 이미지
                    String rawCategory = item.optString("categoryName", "카테고리 없음");
                    String tag = rawCategory.contains(">")
                            ? rawCategory.substring(0, rawCategory.indexOf(">", rawCategory.indexOf(">") + 1))
                            : rawCategory;

                    books.add(new Book(title, author, pubDate, imageUrl, description, tag));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
