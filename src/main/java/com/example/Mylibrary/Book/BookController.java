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
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final GoogleBooksApiService googleBooksApiService;

    @GetMapping("/booksearch")
    public String searchBooks(@RequestParam(value = "query", defaultValue = "java") String query,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model)
    {
        int startIndex = (page - 1) * 20;
        List<Book> books = googleBooksApiService.searchBooks(query, startIndex);
        model.addAttribute("books", books); // 검색 결과를 뷰로 전달
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);  // 현재 페이지
        model.addAttribute("totalPages", 3);
        return "booksearch.html"; // booksearch.html로 반환
    }
    @PostMapping("/save")
    public String saveBook(@ModelAttribute BookDto bookDto) {
        bookService.saveBook(bookDto);
        return "redirect:/mybookshelf";
    }

    @GetMapping("/mybookshelf")
    public String mybookshelf(Model model){
        var result = bookRepository.findAll();
        model.addAttribute("books", result);
        return "mybookshelf.html";
    }
}

@Getter
@Setter

class BookDto{
    private String imageUrl;
    public String title;
    public String author;
    public String publishedDate;

    BookDto(String title, String author, String publishedDate, String imageUrl){
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
    }
}

