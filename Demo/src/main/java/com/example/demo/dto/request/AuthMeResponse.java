package com.example.demo.dto.request;

import java.util.List;

public class AuthMeResponse {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthMeResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    private String username;
    private List<String> roles;
}
