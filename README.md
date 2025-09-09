# 나만의 도서관 (MyLibrary)
프로젝트 명: 나만의 도서관  
진행 기간: 2025.03.10 – 2025.06.10 (3개월)  
개발 환경: Java, Spring Boot, Azure MySQL, JPA, Python, Thymeleaf 등  

## 프로젝트 소개
**왜 필요할까?**  
평소 독서를 할 때 인상 깊은 구절이나 생각을 기록하고 싶지만 흩어진 자료들 때문에 불편함을 느꼈습니다.  
이를 해결하기 위해 읽은 책을 한 곳에 기록·정리하고, 감상과 추천을 공유할 수 있는 플랫폼을 만들게 되었습니다.  

**나만의 도서관이란?**  
읽은 책을 개인 서재에 저장하고 감상문을 남길 수 있습니다.  
다른 사용자와 블로그 형식으로 독서 경험 공유 가능합니다.  
AI 기반 맞춤형 도서 추천을 제공합니다.  

**타겟 사용자**  
독서 기록을 체계적으로 관리하고 싶은 사람  
타인과 독서 취향을 공유하고 싶은 사람  
자신의 취향에 맞는 도서 추천을 받고 싶은 사람  

## 사용 기술 스택
**Frontend** 
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=flat&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=flat&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=flat&logo=javascript&logoColor=%23F7DF1E)  
**Backend** 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=flat&logo=springboot&logoColor=white) ![Python](https://img.shields.io/badge/python-%233776AB.svg?style=flat&logo=python&logoColor=white) ![Flask](https://img.shields.io/badge/flask-%23000.svg?style=flat&logo=flask&logoColor=white)  
**Database** 
![MySQL](https://img.shields.io/badge/mysql-%234479A1.svg?style=flat&logo=mysql&logoColor=white) ![Azure](https://img.shields.io/badge/azure-%230072C6.svg?style=flat&logo=microsoftazure&logoColor=white)  
**Cloud Storage** 
![AWS S3](https://img.shields.io/badge/AWS%20S3-%23FF9900.svg?style=flat&logo=amazonaws&logoColor=white)  
**AI / API** 
![Aladin API](https://img.shields.io/badge/Aladin%20API-%230077B5.svg?style=flat&logo=bookstack&logoColor=white) ![KeyBERT](https://img.shields.io/badge/KeyBERT-%2300ADD8.svg?style=flat&logo=python&logoColor=white) ![Sentence Transformers](https://img.shields.io/badge/SentenceTransformers-%2343853D.svg?style=flat&logo=pytorch&logoColor=white)  


## 파일 구조 (일부)
```
├── Book
│   ├── AladinApiService
│   ├── BookController
│   ├── BookRepository
│   └── RecommendationController
├── Blog
│   ├── BlogController
│   └── BlogRepository
├── Member
│   ├── MemberController
│   ├── MemberRepository
│   └── MyUserDetailsService
├── templates
│   ├── home.html
│   ├── login.html
│   ├── mybookshelf.html
│   ├── recommendation.html
│   └── writeblog.html
```

## 주요 페이지
- 로그인 / 회원가입: 사용자 인증 후 개인 서재 및 추천 기능 제공
- Home 페이지: 월간 베스트셀러와 추천 도서 표시
- 도서 검색: 알라딘 API를 활용하여 국내 도서 검색 및 별점 저장 가능
- 도서 블로그: 감상문 작성, 사진 업로드, 댓글 및 공감 기능 제공
- 나의 서재: 자신이 저장한 책을 한눈에 확인 가능
- 블로그 글 목록: 개인/전체 블로그 글을 인기순, 최신순으로 열람 가능
- 맞춤형 추천: 개인 취향 기반 AI 추천 도서 제공

## 문제 해결
- API 변경: 구글 Books API → 알라딘 API로 전환하여 국내 도서 데이터 활용
- 로그인 시 에러: Spring Security 권한 부여 문제 해결
- 도서 카테고리 매핑: 알라딘 API 하위 카테고리 문제 → 직접 매핑으로 해결

## 배운 점
- 처음부터 끝까지 웹 프로젝트를 완성하며 API 연동, Spring Security, JPA 활용에 대한 이해 심화
- 단순 구현을 넘어 사용자 경험(UX)을 고려한 설계의 중요성 학습
- 실제 배포 가능한 수준으로 마무리하여 협업·문제 해결 능력 강화

## 자료
[캡스톤최종발표.pdf](https://github.com/user-attachments/files/20756786/default.pdf)

## 라이선스
본 프로젝트는 학습 목적으로 제작되었으며, 상업적 용도로 사용되지 않습니다.
