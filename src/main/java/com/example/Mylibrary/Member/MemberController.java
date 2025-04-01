package com.example.Mylibrary.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRespository memberRespository;

    @PostMapping("/addmember")
    public String addMember(@ModelAttribute Member member){
        var hash = new BCryptPasswordEncoder().encode(member.getPassword());
        member.setPassword(hash);
        memberRespository.save(member);
        return "redirect:/login.html";
    }
    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/fail")
    public String fail(){return "fail.html";}

}
