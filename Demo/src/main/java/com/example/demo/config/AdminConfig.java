package com.example.demo.config;

import com.example.demo.enums.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.coyote.http11.Constants;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
public class AdminConfig {
    private final PasswordEncoder passwordEncoder;
    public AdminConfig(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository)
    {
        return args -> {
            if(userRepository.findByUserName("admin").isEmpty()){
                User user = new User();
                user.setUserName("admin");
                user.setPassWord(passwordEncoder.encode("admin123456"));
                HashSet<Roles> roles = new HashSet<>();
                roles.add(Roles.valueOf(Roles.ADMIN.name()));
                user.setRoles(roles);
                 userRepository.save(user);
                System.out.println("Admin đã được tạo");

            }

        };

    }
}
