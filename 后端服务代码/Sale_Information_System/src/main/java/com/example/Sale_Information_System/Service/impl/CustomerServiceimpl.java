package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.CustomerMapper;
import com.example.Sale_Information_System.Service.CustomerService;
import com.example.Sale_Information_System.pojo.Customer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceimpl implements  CustomerService {
    private final CustomerMapper customerMapper;
    public CustomerServiceimpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.getAllCustomers();
    }
    @Override
    public Customer getCustomerById(String customerId) {
        return customerMapper.getCustomerById(customerId);
    }
    @Override
    public void insert(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isBlank()) {
            customer.setCustomerId(generateCustomerId());
        }
        if (customer.getPassword() == null || customer.getPassword().isBlank()) {
            customer.setPassword(new BCryptPasswordEncoder().encode("123456"));
        }
        customerMapper.insert(customer);
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
    public void update(Customer customer) {
        customerMapper.update(customer);

    }
    @Override
    public void delete(String customerId) {
        customerMapper.delete(customerId);
    }
}
