package com.example.Sale_Information_System.pojo;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
    private String role;
}
