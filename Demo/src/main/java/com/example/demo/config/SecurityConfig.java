package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                // trang gốc
                                "/index.html",
                                "/layout.html",
                                "/pages/home.html",
                                "/pages/login.html",
                                "/pages/register.html",
                                "/pages/job-detail.html",
                                "/pages/**",
                                "/js/home.js",    // file HTML của bạn
                                "/css/**",           // CSS
                                "/js/**",
                                "/jobs/**", // JS
                                "/images/**",        // ảnh
                                "/uploads/**",       // logo công ty
                                "/home/jobs",        // API public
                                "/auth/**",
                                "/users/**",
                                "/employer/**",
                                "/candidate/**",
                                "/users/createUsers",
                                "/users/createEmployers",
                                "/category/list"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
