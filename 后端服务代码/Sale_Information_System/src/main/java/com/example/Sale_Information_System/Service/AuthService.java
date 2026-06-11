package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Admin;
import com.example.Sale_Information_System.pojo.Customer;
import com.example.Sale_Information_System.pojo.LoginRequest;
import com.example.Sale_Information_System.pojo.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse register(Customer customer);
    LoginResponse registerAdmin(Admin admin);
    void changePassword(String customerId, String oldPassword, String newPassword);
}
