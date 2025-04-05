package com.example.Mylibrary.Blog;

import com.example.Mylibrary.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String story;
    private String writer;
    private LocalDateTime createdAt;
    private String imgurl;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // 현재 시간을 자동 설정
    }


}
