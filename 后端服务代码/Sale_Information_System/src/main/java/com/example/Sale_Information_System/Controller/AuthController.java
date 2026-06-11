package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.AuthService;
import com.example.Sale_Information_System.pojo.Admin;
import com.example.Sale_Information_System.pojo.Customer;
import com.example.Sale_Information_System.pojo.LoginRequest;
import com.example.Sale_Information_System.pojo.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse res = authService.login(request);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        try {
            LoginResponse res = authService.register(customer);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        try {
            LoginResponse res = authService.registerAdmin(admin);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, @RequestHeader("Authorization") String auth) {
        try {
            String token = auth.replace("Bearer ", "");
            var claims = com.example.Sale_Information_System.util.JwtUtil.parseToken(token);
            String userId = claims.get("userId", String.class);
            authService.changePassword(userId, body.get("oldPassword"), body.get("newPassword"));
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
