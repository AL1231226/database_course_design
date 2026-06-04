package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(String customerId);
}
