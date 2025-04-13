package com.example.Mylibrary.Book;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksApiService {

    @Value("${google.api.key}")
    private String apiKey;

    public List<Book> searchBooks(String query, int startIndex) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&startIndex=" + startIndex + "&maxResults=40&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        // getForObject 사용, 응답 본문을 바로 Book 객체 리스트로 변환
        String response = restTemplate.getForObject(url, String.class);

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.optJSONArray("items");

            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject volumeInfo = items.getJSONObject(i).optJSONObject("volumeInfo");
                    if (volumeInfo != null) {
                        String title = volumeInfo.optString("title", "제목 없음");
                        String author = volumeInfo.optString("authors", "저자 없음");
                        String publishedDate = volumeInfo.optString("publishedDate", "출판일 없음");
                        String description = volumeInfo.optString("description", "설명 없음");
                        String imageUrl = null;
                        JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                        if (imageLinks != null) {
                            imageUrl = imageLinks.optString("thumbnail");  // 썸네일 이미지
                        }
                        books.add(new Book(title, author, publishedDate, imageUrl, description));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
