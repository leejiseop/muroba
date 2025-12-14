package com.example.muroba.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/**")
                .allowedOrigins("*")
//                .allowedOrigins("http://localhost:3000"); // react
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("*");
//                .allowCredentials(true) // true로 설정하면 allowedOrigins("*")와 같이 사용불가 (보안 문제)
    } // test

}
