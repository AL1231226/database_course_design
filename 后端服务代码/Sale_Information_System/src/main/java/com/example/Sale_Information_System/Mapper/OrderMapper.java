package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    void updateFeedback(@Param("orderId") String orderId, @Param("adminFeedback") String adminFeedback);

    // 按顾客查询
    List<Orders> getMyOrders(@Param("customerId") String customerId);
    List<Orders> getMyUnpaidOrders(@Param("customerId") String customerId);
    List<Orders> getMyUnshippedOrders(@Param("customerId") String customerId);
    List<Orders> getMyCompletedOrders(@Param("customerId") String customerId);
    List<Orders> getMyCancelledOrders(@Param("customerId") String customerId);

}
