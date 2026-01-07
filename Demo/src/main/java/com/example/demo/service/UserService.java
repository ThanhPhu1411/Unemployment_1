package com.example.demo.service;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.enums.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder )
    {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }
    public User createUser (UserCreationRequest request)
    {
        if(userRepository.existsByUserName(request.getUserName()))
            throw new RuntimeException("Tên người dùng đã tồn tại");
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        HashSet<Roles> roles = new HashSet<>();
        roles.add(Roles.valueOf(Roles.USER.name()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public User createEmployer (UserCreationRequest request)
    {
        if(userRepository.existsByUserName(request.getUserName()))
            throw new RuntimeException("Tên người dùng đã tồn tại");
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        HashSet<Roles> roles = new HashSet<>();
        roles.add(Roles.valueOf(Roles.EMPLOYER.name()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public User geUser(UUID id)
    {
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser( UUID userId, UserUpdateRequest request)
    {
       User user = geUser(userId);
       if(user !=null)
       {
           user.setPassWord(passwordEncoder.encode(request.getPassWord()));
       }
       return userRepository.save(user);
    }
    public void deleteUser(UUID userId)
    {
        userRepository.deleteById(userId);
    }
}
