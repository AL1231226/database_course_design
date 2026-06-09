package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Orders;

import java.util.List;

public interface OrderService {
    List<Orders> getAllOrders();
    List<Orders> getUnpaidOrders();//待付款
    List<Orders> getUnshippedOrders();//待发货
    List<Orders> getCompletetedOrders();//已完成
    Orders getOrderById(String orderId);
    void insert(Orders orders);
    void confirmSupply(String orderId);
    void updatePayment(String orderId, String paymentStatus, String paymentInfo);
    List<Orders> getCancelledOrders();
    void cancelOrder(String orderId);
}
