package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.CustomerMapper;
import com.example.Sale_Information_System.Service.CustomerService;
import com.example.Sale_Information_System.pojo.Customer;
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
}
