package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrderId(String orderId);
    void insert(OrderDetail detail);
}
