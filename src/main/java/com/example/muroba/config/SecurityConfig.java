package com.example.muroba.config;

import com.example.muroba.jwt.JwtUtil;
import com.example.muroba.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    //AuthenticationManager(interface) Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity // hasRole, permitAll, authenticated, denyAll ...
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/", "/api/members/**", "/api/email/**", "/login").permitAll()
                        .requestMatchers("/threads/**", "/messages/**",
                                "/mypage/**", "/mypage_modify/**", "/gallery/**"
                                ).hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated() // 나머지 모든 경로들 로그인 인증 필요
                );

        // JWT 방식

        // JWT를 사용할 땐 session이 stateless 상태이기 때문에, 특별히 CSRF 공격을 방어할 필요는 없다
        httpSecurity
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        httpSecurity
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        httpSecurity
                .httpBasic((auth) -> auth.disable());

        httpSecurity
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

//        httpSecurity
//                .formLogin((auth) -> auth
//                        .loginPage("/") // 로그인이 필요하면 로그인 페이지로 redirect
//                        .loginProcessingUrl("/login_process") // 로그인 데이터를 받는 곳 (id, pwd)
//                        .defaultSuccessUrl("/threads", true)
//                        .permitAll() // 로그인 페이지는 모두가 접근 가능
//                );

        // session 방식
        /*


        httpSecurity
                .csrf((auth) -> auth.disable() // 임시
                );

        httpSecurity
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 최대 세션 개수(다중로그인 가능 개수)
                        .maxSessionsPreventsLogin(true) // 세션 초과되면 기존 로그인을 로그아웃 시킬지 // true: 새로운 로그인 차단, false: 새로운 로그인 진행
                );

        httpSecurity
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        httpSecurity
                .sessionManagement((auth) -> auth
                        // - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
                        // - sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
                        // - sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경

                        .sessionFixation().changeSessionId()
                );
         */

        return httpSecurity.build();
    }

}
