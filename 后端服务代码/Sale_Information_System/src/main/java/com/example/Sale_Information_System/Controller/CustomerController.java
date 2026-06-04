package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.CustomerService;
import com.example.Sale_Information_System.pojo.Customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @RequestMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @RequestMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }
}
