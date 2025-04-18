package com.example.Mylibrary.Book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {
    @Autowired
    private final BookRepository bookRepository;
    private final AladinApiService aladinApiService;

    @GetMapping("/booksearch")
    public String searchBooks(@RequestParam(value = "query", defaultValue = "") String query,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        int startIndex = (page - 1) * 20;  // 알라딘은 page 기준이 1-base, MaxResults 지정 가능
        List<Book> books = aladinApiService.searchBooks(query, page);  // aladinApiService 사용
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3);
        return "booksearch.html";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/mybookshelf";
    }

    @GetMapping("/mybookshelf")
    public String mybookshelf(Model model) {
        var result = bookRepository.findAll();
        model.addAttribute("books", result);
        return "mybookshelf.html";
    }
}

