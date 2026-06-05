package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> getAllCustomers();
    Customer getCustomerById(String customerId);
    void insert(Customer customer);
    void update(Customer customer);
    void delete(String customerId);

}
