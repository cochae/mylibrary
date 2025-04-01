package com.example.Mylibrary.Blog;

import com.example.Mylibrary.S3Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    private final BlogRepository blogRepository;
    private final S3Service s3Service;

    public BlogController(BlogRepository blogRepository, S3Service s3Service) {
        this.blogRepository = blogRepository;
        this.s3Service = s3Service;
    }

    @PostMapping("/addblog")
    public String addblog(@ModelAttribute Blog blog) {
        blogRepository.save(blog);
        return "redirect:/bloglist.html";
    }

    @GetMapping("/writeblog")
    public String blog() {
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
        if (result.isPresent()) {
            model.addAttribute("blog", result.get());
            return "viewblog.html";
        }
        return "redirect:/home";
    }
    @GetMapping("/bloglist")
    String bloglist(Model model) {
        List<Blog> result = blogRepository.findAll();
        model.addAttribute("blogs", result);
        return "bloglist.html";
    }

}
