package com.example.Mylibrary.Book;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class RecommendationController {
    private final RestTemplate restTemplate;
    public RecommendationController(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }
    @GetMapping("/recommendation")
    public String getRecommendation(Model model) {
        String url = "http://localhost:5000/recommend";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        model.addAttribute("recommendations", response.getBody());
        return "recommendation";
    }
}
