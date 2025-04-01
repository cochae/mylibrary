package com.example.Mylibrary.BestSeller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final BestSellerRepository bestSellerRepository;
    @GetMapping("/home")
    public String home(Model model){
        List<BestSeller> result = bestSellerRepository.findAll();
        model.addAttribute("books", result);
        return "home.html";
    }
}
