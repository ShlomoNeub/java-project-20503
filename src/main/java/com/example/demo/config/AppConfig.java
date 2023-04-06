package com.example.demo.config;

import com.example.demo.config.interceptor.AuthPayloadArgumentResolver;
import com.example.demo.db.repo.JwtRepo;
import com.example.demo.db.repo.UserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    final UserRepo userRepo;

    final JwtRepo jwtRepo;

    public AppConfig(UserRepo userRepo, JwtRepo jwtRepo) {
        this.userRepo = userRepo;
        this.jwtRepo = jwtRepo;
    }


    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthPayloadArgumentResolver(userRepo, jwtRepo));
    }


    // Override other methods of the WebMvcConfigurer interface as needed
}