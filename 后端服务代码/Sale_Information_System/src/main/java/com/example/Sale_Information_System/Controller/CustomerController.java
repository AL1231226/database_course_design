package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.CustomerService;
import com.example.Sale_Information_System.pojo.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        customerService.insert(customer);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customerService.update(customer);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
