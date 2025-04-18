package com.example.Mylibrary.Book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publishedDate;
    private String imageUrl; // 🔥 이미지 URL 추가!
    @Column(columnDefinition = "TEXT")
    private String description;
    private String category; // 카테고리 id
    private String tag; // 카테고리 name

    // 기본 생성자
    public Book() {}

    // 매개변수 있는 생성자
    public Book(
            String title,
            String author,
            String publishedDate,
            String imageUrl,
            String description,
            String tag
    ) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.tag = tag;
    }
}
