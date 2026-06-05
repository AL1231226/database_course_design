package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Orders> getAllOrders();
    Orders getOrderById(String orderId);
    void insert(Orders order);


}
