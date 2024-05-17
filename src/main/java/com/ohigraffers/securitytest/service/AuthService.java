package com.ohigraffers.securitytest.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //추가 테스트를 위한 로직
        if (username.equals("admin")) {
            log.info("관리자로 로그인 되었음");
            return User.builder()
                       .username(username)
                       .password("$2a$10$COvazywgZPXseeKaYhruh.pAYYfcSeGO5aSrHOsLZN0X8joNwW2dW")  // "1234"
                       .roles("ADMIN")
                       .build();
        }

        return User.builder()
                .username(username)
                .password("$2a$10$COvazywgZPXseeKaYhruh.pAYYfcSeGO5aSrHOsLZN0X8joNwW2dW")  // "1234"
                .roles("USER")
                .build();
    }

    public void saveAuthentication(String username) {
        UserDetails user;
        //추가 테스트를 위한 로직
        if (username.equals("admin")) {
            user =User.builder()
                       .username(username)
                       .password("$2a$10$COvazywgZPXseeKaYhruh.pAYYfcSeGO5aSrHOsLZN0X8joNwW2dW")  // "1234"
                       .roles("ADMIN")
                       .build();
        }
        else{
            user = User.builder()
                       .username(username)
                       .password("$2a$10$COvazywgZPXseeKaYhruh.pAYYfcSeGO5aSrHOsLZN0X8joNwW2dW")
                       .roles("USER")
                       .build();
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }








}
