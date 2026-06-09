package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Orders> getAllOrders();
    Orders getOrderById(String orderId);
    void insert(Orders order);
    List<Orders> getUnpaidOrders();//待付款
    List<Orders> getUnshippedOrders();//待发货
    List<Orders> getCompletetedOrders();//已完成
    void confirmSupply(String orderId);
    void updatePayment(Orders orders);
    List<Orders> getCancelledOrders();
    void cancelOrder(String orderId);

}
