package com.example.Mylibrary.Comment;

import com.example.Mylibrary.Blog.Blog;
import com.example.Mylibrary.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String story;
    private String writer;
    private int likeCount;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
