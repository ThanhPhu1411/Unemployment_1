package com.example.demo.service;

import com.example.demo.dto.request.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository,
    PasswordEncoder passwordEncoder,
                       JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login (AuthRequest request)
    {
        var userOpt  = userRepository.findByUserName(request.getUserName());
        if(userOpt .isEmpty())
        {
            return "Tài khoản không tồn tại ";
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassWord(), user.getPassWord())) {
            return "Sai mật khẩu";
        }
    String asscessToken = jwtService.generateToken(user);
        return  asscessToken;
    }
}
