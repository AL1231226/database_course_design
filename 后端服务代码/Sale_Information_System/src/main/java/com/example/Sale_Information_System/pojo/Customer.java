package com.example.Sale_Information_System.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String customerId; //顾客代码
    private String customerName; //顾客名称
    private String contactName; //姓名
    private String address;  //地址
    private String zipCode; //邮编
    private String phone; // 电话
}
