package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Orders;

import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();
    Orders getOrderById(String orderId);
    void insert(Orders orders);
}
