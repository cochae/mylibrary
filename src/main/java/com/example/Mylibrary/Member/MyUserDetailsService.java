package com.example.Mylibrary.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRespository memberRespository;

    @Override
    public UserDetails loadUserByUsername(String displayname) throws UsernameNotFoundException {
        // displayname으로 회원 조회
        var result = memberRespository.findByDisplayname(displayname);

        // 회원이 없으면 예외를 던짐
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("아이디 없음");
        }

        // 조회된 회원 정보 가져오기
        var user = result.get();

        // 권한 설정 (일반유저 권한 부여)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_일반유저"));  // 권한에 ROLE_ 접두어 사용

        // User 객체 반환 (아이디, 비밀번호, 권한 정보)
        return new User(user.getDisplayname(), user.getPassword(), authorities);
    }
}
