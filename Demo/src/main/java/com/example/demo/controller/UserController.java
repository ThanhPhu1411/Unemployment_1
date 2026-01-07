package com.example.demo.controller;

import com.example.demo.dto.request.APIResponse;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService =userService;
    }
    @PostMapping("/createUsers")
    public APIResponse<User> createUser(@RequestBody @Valid UserCreationRequest request)
    {
        APIResponse<User> apiResponse = new APIResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }
    @PostMapping("/createEmployers")
    public APIResponse<User> createEmployer(@RequestBody @Valid UserCreationRequest request)
    {
        APIResponse<User> apiResponse = new APIResponse<>();
        apiResponse.setResult(userService.createEmployer(request));
        return apiResponse;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<User> getUsers(){
        return userService.getUsers();

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") UUID id)
    {
        return  userService.geUser(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") UUID id ,@RequestBody UserUpdateRequest request)
    {
        return userService.updateUser(id,request);
    }
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public String deleteUser (@PathVariable("id") UUID id){
        userService.deleteUser(id);
        return "Nguời dùng đã được xóa ";
    }

}
