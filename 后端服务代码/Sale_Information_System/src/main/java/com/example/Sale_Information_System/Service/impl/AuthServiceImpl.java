package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.AdminMapper;
import com.example.Sale_Information_System.Mapper.CustomerMapper;
import com.example.Sale_Information_System.Service.AuthService;
import com.example.Sale_Information_System.pojo.Admin;
import com.example.Sale_Information_System.pojo.Customer;
import com.example.Sale_Information_System.pojo.LoginRequest;
import com.example.Sale_Information_System.pojo.LoginResponse;
import com.example.Sale_Information_System.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerMapper customerMapper;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(CustomerMapper customerMapper, AdminMapper adminMapper) {
        this.customerMapper = customerMapper;
        this.adminMapper = adminMapper;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if ("customer".equals(request.getRole())) {
            java.util.List<Customer> customers = customerMapper.findByAccount(request.getAccount());
            if (customers == null || customers.isEmpty()) {
                throw new RuntimeException("账号或密码错误");
            }
            Customer matched = null;
            for (Customer c : customers) {
                if (encoder.matches(request.getPassword(), c.getPassword())) {
                    matched = c;
                    break;
                }
            }
            if (matched == null) {
                throw new RuntimeException("账号或密码错误");
            }
            String token = JwtUtil.generateToken(matched.getCustomerId(), "customer", matched.getCustomerName());
            return new LoginResponse(token, matched.getCustomerId(), matched.getCustomerName(), "customer");
        } else if ("admin".equals(request.getRole())) {
            Admin admin = adminMapper.findByUsername(request.getAccount());
            if (admin == null || !encoder.matches(request.getPassword(), admin.getPassword())) {
                throw new RuntimeException("账号或密码错误");
            }
            String token = JwtUtil.generateToken(String.valueOf(admin.getAdminId()), "admin", admin.getAdminName());
            return new LoginResponse(token, String.valueOf(admin.getAdminId()), admin.getAdminName(), "admin");
        }
        throw new RuntimeException("无效的角色类型");
    }

    @Override
    public LoginResponse register(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isBlank()) {
            customer.setCustomerId(generateCustomerId());
        }
        customer.setPassword(encoder.encode(customer.getPassword()));
        customerMapper.insert(customer);

        String token = JwtUtil.generateToken(customer.getCustomerId(), "customer", customer.getCustomerName());
        return new LoginResponse(token, customer.getCustomerId(), customer.getCustomerName(), "customer");
    }

    private String generateCustomerId() {
        String maxId = customerMapper.getMaxCustomerId();
        if (maxId == null) {
            return "CUST001";
        }
        int num = Integer.parseInt(maxId.replaceAll("\\D", ""));
        return String.format("CUST%03d", num + 1);
    }

    @Override
    public void changePassword(String customerId, String oldPassword, String newPassword) {
        Customer customer = customerMapper.getCustomerById(customerId);
        if (customer == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!encoder.matches(oldPassword, customer.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        customerMapper.updatePassword(customerId, encoder.encode(newPassword));
    }

    @Override
    public LoginResponse registerAdmin(Admin admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminMapper.insert(admin);

        String token = JwtUtil.generateToken(String.valueOf(admin.getAdminId()), "admin", admin.getAdminName());
        return new LoginResponse(token, String.valueOf(admin.getAdminId()), admin.getAdminName(), "admin");
    }
}
