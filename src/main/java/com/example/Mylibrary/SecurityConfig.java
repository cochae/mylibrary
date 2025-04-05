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
        // 정적 리소스 경로 및 로그인/회원가입 경로 허용
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/addmember", "/", "/login", "/register", "/css/**", "/images/**", "/js/**", "/index.html", "/favicon.ico").permitAll()  // /index.html을 permitAll로 설정
                        .anyRequest().authenticated() // 그 외 모든 요청은 로그인 필수
        );


        // 로그인 페이지 설정
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")  // 커스텀 로그인 페이지 경로
                        .usernameParameter("username")
                        .defaultSuccessUrl("/home", true)  // 로그인 성공 후 /home으로 리디렉션
                        .failureUrl("/fail") // 로그인 실패 시 /fail로 리디렉션
        );

        http.logout(logout ->
                logout.logoutUrl("/logout") // 로그아웃은 오직 `/logout` URL에서만 실행
                        .logoutSuccessUrl("/home") // 로그아웃 후 홈으로 이동
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
        );
        http.headers(headers ->
                headers.cacheControl(cache -> cache.disable()) // 캐시 비활성화
        );
        return http.build();
    }
}
