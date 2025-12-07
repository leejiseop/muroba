package com.example.muroba.jwt;

import com.example.muroba.config.RedisUtil;
import com.example.muroba.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("LoginFilter -> attemptAuthentication");

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) {

        System.out.println("LoginFilter -> successfulAuthentication");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // member 가져오기
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // 권한을 가져온다
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); // 권한은 여러개일 수 있다
        GrantedAuthority auth = iterator.next(); // 리스트 중 첫 번째 권한을 가져온다
        String role = auth.getAuthority();

        // 1. Access Token
        String access_token = jwtUtil.createJwt(username, role, 1000*60*5L); // 5분

        Cookie access_token_cookie = new Cookie("access_token", access_token);
        access_token_cookie.setHttpOnly(true);
        access_token_cookie.setSecure(false); // https 전환시 수정해야함
        access_token_cookie.setPath("/");
        access_token_cookie.setMaxAge(60*5); // 5분

        // access token 만료 시 자동 재발급
        // refresh token 만료 시 재로그인

        // 2. Refresh Token (14일)
        String refresh_token = jwtUtil.createJwt(username, role, 1000*60*60*24*14L); // 14일

        Cookie refresh_token_cookie = new Cookie("refresh_token", refresh_token);
        refresh_token_cookie.setHttpOnly(true);
        refresh_token_cookie.setSecure(false); // https 전환시 수정해야함
        refresh_token_cookie.setPath("/");
        refresh_token_cookie.setMaxAge(60*60*24*14); // 14일

        // 3. Refresh Token을 Redis에 저장
        redisUtil.setDataExpire("refresh_token:" + username, refresh_token, 60*60*24*14L); // 14일

        System.out.println("login access_token: " + access_token);
        System.out.println("login refresh_token: " + refresh_token);

        response.addCookie(access_token_cookie);
        response.addCookie(refresh_token_cookie);
//        response.addHeader("Authorization", "Bearer " + access_token);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
        System.out.println("LoginFilter -> unsuccessfulAuthentication");
    }

}