package com.example.Mylibrary.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
        memberRespository.flush();

        return "redirect:/login.html";
    }
    @GetMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인된 상태라면 자동 로그아웃
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "register.html"; // 회원가입 페이지 반환
    }


    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/fail")
    public String fail(){return "fail.html";}

}
