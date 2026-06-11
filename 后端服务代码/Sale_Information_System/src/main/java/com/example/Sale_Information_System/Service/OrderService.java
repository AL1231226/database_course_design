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
    void updateFeedback(String orderId, String adminFeedback);

    // 当前顾客的订单
    List<Orders> getMyOrders(String customerId);
    List<Orders> getMyUnpaidOrders(String customerId);
    List<Orders> getMyUnshippedOrders(String customerId);
    List<Orders> getMyCompletedOrders(String customerId);
    List<Orders> getMyCancelledOrders(String customerId);
}
