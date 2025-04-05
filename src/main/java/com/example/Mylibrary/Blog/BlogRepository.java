package com.example.Mylibrary.Blog;

import com.example.Mylibrary.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByMember(Member member);

}

