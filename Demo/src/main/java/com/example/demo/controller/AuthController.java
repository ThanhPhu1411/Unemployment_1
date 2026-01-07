package com.example.demo.controller;

import com.example.demo.dto.request.AuthMeResponse;
import com.example.demo.dto.request.AuthRequest;
import com.example.demo.service.AuthService;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }
    @PostMapping("/log-in")
    public String login(@RequestBody AuthRequest request)
    {
        return authService.login(request);
    }
    @GetMapping("/me")
    public AuthMeResponse me(Authentication authentication) {

        return new AuthMeResponse(
                authentication.getName(), // username
                authentication.getAuthorities()
                        .stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toList())
        );
    }

}
