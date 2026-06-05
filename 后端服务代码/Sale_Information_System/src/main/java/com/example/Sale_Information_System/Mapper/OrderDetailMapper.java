package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    List<OrderDetail> findByOrderId(String orderId);
    void insert(OrderDetail detail);
}
