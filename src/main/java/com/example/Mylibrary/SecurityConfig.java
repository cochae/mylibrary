package com.example.Mylibrary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // 정적 리소스 경로 및 로그인/회원가입 경로 허용
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/", "/login", "/register", "/css/**", "/images/**", "/js/**", "/index.html").permitAll()  // /index.html을 permitAll로 설정
                        .anyRequest().authenticated() // 그 외 모든 요청은 로그인 필수
        );


        // 로그인 페이지 설정
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")  // 커스텀 로그인 페이지 경로
                        .usernameParameter("displayname") // 로그인 폼에서 displayname 사용
                        .defaultSuccessUrl("/home", false)  // 로그인 성공 후 /home으로 리디렉션
                        .failureUrl("/fail") // 로그인 실패 시 /fail로 리디렉션
        );
        // 로그아웃 설정
        http.logout(logout ->
                logout.logoutUrl("/logout").logoutSuccessUrl("/home") // 로그아웃 후 /로 이동
        );

        return http.build();
    }
}
