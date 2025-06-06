package com.example.Mylibrary.Book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Book {
    @Id
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String title;
    private String author;
    private String publishedDate;
    private String imageUrl; // 🔥 이미지 URL 추가!
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer category; // 카테고리 id
    private String tag; // 카테고리 name
    private Integer rating;
    private String isbn;

    // 기본 생성자
    public Book() {}

    // 매개변수 있는 생성자
    public Book(
            String title,
            String author,
            String publishedDate,
            String imageUrl,
            String description,
            String tag,
            String isbn
    ) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.tag = tag;
        this.isbn=isbn;
    }
}
