package com.example.Mylibrary.Member;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
public class CustomUser extends User {
    private String displayname;
    private String id;
    public CustomUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
    }
}
