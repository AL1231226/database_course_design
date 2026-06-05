package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.CustomerMapper;
import com.example.Sale_Information_System.Service.CustomerService;
import com.example.Sale_Information_System.pojo.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
            customer.setCustomerId(UUID.randomUUID().toString());
        }
        customerMapper.insert(customer);
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
