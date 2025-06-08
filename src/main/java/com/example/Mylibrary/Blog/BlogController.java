package com.example.Mylibrary.Blog;

import com.example.Mylibrary.Comment.Comment;
import com.example.Mylibrary.Comment.CommentRepository;
import com.example.Mylibrary.Member.CustomUser;
import com.example.Mylibrary.Member.Member;
import com.example.Mylibrary.Member.MemberRepository;
import com.example.Mylibrary.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Controller
public class BlogController {
    private final BlogRepository blogRepository;
    private final S3Service s3Service;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/addblog")
    public String addblog(@ModelAttribute Blog blog, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findByUsername(user.getUsername()).orElseThrow();
        blog.setWriter(user.getUsername());
        blog.setMember(member);
        blogRepository.save(blog);
        return "redirect:/mybloglist";
    }

    @GetMapping("/writeblog")
    public String write() {
        return "writeblog.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("img/" + filename);
        return result;
    }

    @GetMapping("/viewblog/{id}")
    String viewblog(@PathVariable Integer id, Model model) {
       Optional<Blog> result = blogRepository.findById(id);
       List<Comment> comments = commentRepository.findByBlog_Id(id);
        if (result.isPresent()) {
            model.addAttribute("blog", result.get());
            model.addAttribute("comments", comments);
            return "viewblog.html";
        }
        else
            return "redirect:/bloglist";
    }

    @GetMapping("/mybloglist")
    String mybloglist(Model model, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findByUsername(user.getUsername()).orElseThrow();
        List<Blog> result = blogRepository.findByMember(member);
        model.addAttribute("blogs", result);
        return "mybloglist.html";
    }
    @GetMapping("/bloglist")
    String bloglist(Model model) {
        List<Blog> result = blogRepository.findAll();
        List<Blog> like = blogRepository.findTop3ByOrderByLikeCountDesc();
        model.addAttribute("blogs", result);
        model.addAttribute("likes", like);

        return "bloglist.html";
    }
    @PostMapping("/like")
    String like(int id){
        Optional<Blog> result = blogRepository.findById(id);
        Blog blog = result.get();
        blog.setLikeCount(blog.getLikeCount()+1);
        blogRepository.save(blog);
        return "redirect:/viewblog/" + id;
    }
}
