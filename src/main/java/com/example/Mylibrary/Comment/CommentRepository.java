package com.example.Mylibrary.Comment;

import com.example.Mylibrary.Blog.Blog;
import com.example.Mylibrary.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBlog_Id(Integer blog_id);

}
