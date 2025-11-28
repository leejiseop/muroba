package com.example.muroba.jwt;


import com.example.muroba.dto.CustomUserDetails;
import com.example.muroba.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 검증 필터
// HTTP 요청시 header의 Authorization 키에 JWT 토큰을 넣고 전송
// OncePerRequestFilter: 요청에 대해서 한번만 동작하는 필터
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JwtFilter -> doFilterInternal");

        // request에서 Authorization 헤더를 찾음
//        String authorization = request.getHeader("Authorization");

        // cookie 에서 토큰 가져오기
        String access_token = null;
        String refresh_token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    access_token = cookie.getValue();
                }
                if (cookie.getName().equals("refresh_token")) {
                    refresh_token = cookie.getValue();
                }
            }
        }

        System.out.println("jwtfilter access: " + access_token);
        System.out.println("jwtfilter refresh: " + refresh_token);

        //Authorization 헤더 검증
//        if (access_token == null || !access_token.startsWith("Bearer ")) { // 토큰이 없거나 접두사가 이상하다
//        if (access_token == null) { // 토큰이 없다
//
//            System.out.println("token null");
//            filterChain.doFilter(request, response); // request와 response를 다음 필터로 넘겨준다
//
//            return; //조건이 해당되면 메소드 종료 (필수)
//        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
//        String token = access_token.split(" ")[1];

        //토큰 소멸 시간 검증
        if (access_token == null || jwtUtil.isExpired(access_token)) {

            System.out.println("access token expired");

            // 1. refresh token 없으면 재로그인
            if (refresh_token == null) {
                System.out.println("no refresh token -> login required");
                filterChain.doFilter(request, response);
                return;
            }

            // 2. refresh token 유효성 검사 -> 재로그인
            if (jwtUtil.isExpired(refresh_token)) {
                System.out.println("refresh token expired -> login required");
                filterChain.doFilter(request, response);
                return;
            }

            // 3. refresh token 유효 -> access token 재발급
            String username = jwtUtil.getUsername(refresh_token);
            String role = jwtUtil.getRole(refresh_token);

            String new_access_token = jwtUtil.createJwt(username, role, 1000*60*5L);

            // 4. 새 access token cookie로 생성
            Cookie new_access_token_cookie = new Cookie("access_token", new_access_token);

            new_access_token_cookie.setHttpOnly(true);
            new_access_token_cookie.setSecure(false);
            new_access_token_cookie.setPath("/");
            new_access_token_cookie.setMaxAge(60 * 30); // 30분
            response.addCookie(new_access_token_cookie);

            System.out.println("new access token issued");

            // 5. access token 갱신 후 인증 이어서 진행
            access_token = new_access_token;

        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(access_token);
        String role = jwtUtil.getRole(access_token);

        // SecurityContextHolder 세션에 임시 저장할 member - 권한 인증용?
        Member newMember = Member.builder()
                .email(username)
                .password("temppassword") // 임시
                .nickname("tempnickname")
                .country("temppcountry")
                .role(role) // 임시
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(newMember);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

}
