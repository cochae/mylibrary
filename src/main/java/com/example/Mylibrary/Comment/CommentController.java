package com.example.Mylibrary.Comment;

import com.example.Mylibrary.Blog.Blog;
import com.example.Mylibrary.Blog.BlogRepository;
import com.example.Mylibrary.Member.CustomUser;
import com.example.Mylibrary.Member.Member;
import com.example.Mylibrary.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BlogRepository blogRepository;
    @PostMapping("/addcomment")
    public String addcomment(@ModelAttribute Comment comment, Authentication auth, @RequestParam Integer blog_id){
        CustomUser user = (CustomUser) auth.getPrincipal();
        Blog blog = blogRepository.findById(blog_id).orElseThrow();
        comment.setWriter(user.getUsername());
        comment.setBlog(blog);
        commentRepository.save(comment);
        return "redirect:/viewblog/" + blog_id;
    }
}
