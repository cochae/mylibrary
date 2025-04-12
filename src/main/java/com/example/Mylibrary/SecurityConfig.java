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
                auth.anyRequest().permitAll()
        );



        // 로그인 페이지 설정
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")  // 커스텀 로그인 페이지 경로
                        .usernameParameter("username")
                        .defaultSuccessUrl("/home", false)  // 로그인 성공 후 /home으로 리디렉션
                        .failureUrl("/fail") // 로그인 실패 시 /fail로 리디렉션
        );

        http.logout(logout ->
                logout.logoutUrl("/logout") // 로그아웃은 오직 `/logout` URL에서만 실행
                        .logoutSuccessUrl("/home") // 로그아웃 후 홈으로 이동
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
        );
        return http.build();
    }
}
