package com.example.Mylibrary.Book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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
        // 카테고리 이름을 가져와서 카테고리 ID를 찾고, Book 객체에 카테고리 ID를 설정합니다.
        Integer categoryId = CategoryMapper.getCategoryId(book.getTag());  // 카테고리 이름 -> ID로 변환

        if (categoryId != null) {
            book.setCategory(String.valueOf(categoryId));  // 카테고리 ID를 Book 객체에 설정
        } else {
            // 카테고리 ID가 null이면 로깅을 추가하여 원인 파악
            System.out.println("Category ID가 없습니다: " + book.getTag());
        }

        // 책 저장
        bookRepository.save(book);  // 저장된 책을 DB에 저장
        return "redirect:/mybookshelf";  // 저장 후 책 목록으로 리다이렉트
    }


    @GetMapping("/mybookshelf")
    public String mybookshelf(Model model) {
        var result = bookRepository.findAll();
        model.addAttribute("books", result);
        return "mybookshelf.html";
    }
    @PostMapping("/delete")
    public String delete(int id){
        Optional<Book> result = bookRepository.findById(id);
        bookRepository.delete(result.get());
        return "redirect:/mybookshelf";
    }

}

