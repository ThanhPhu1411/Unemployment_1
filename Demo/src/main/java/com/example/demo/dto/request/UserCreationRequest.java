package com.example.demo.dto.request;

import com.example.demo.enums.Roles;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Setter
@Getter
public class UserCreationRequest {
    private String userName;
    @Size(min = 6, message = "Password phải ít nhất 6 ký tự")
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
