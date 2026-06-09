package com.example.Sale_Information_System.Service.impl;

import com.example.Sale_Information_System.Mapper.OrderDetailMapper;
import com.example.Sale_Information_System.Mapper.OrderMapper;
import com.example.Sale_Information_System.Mapper.ProductMapper;
import com.example.Sale_Information_System.Service.OrderService;
import com.example.Sale_Information_System.pojo.OrderDetail;
import com.example.Sale_Information_System.pojo.Orders;
import com.example.Sale_Information_System.pojo.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final ProductMapper productMapper;
    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.productMapper = productMapper;
    }


    @Override
    public List<Orders> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Orders> getUnpaidOrders() {
        return orderMapper.getUnpaidOrders();
    }

    @Override
    public List<Orders> getUnshippedOrders() {
        return orderMapper.getUnshippedOrders();
    }

    @Override
    public List<Orders> getCompletetedOrders() {
        return orderMapper.getCompletetedOrders();
    }


    @Override
    public Orders getOrderById(String orderId) {
        return orderMapper.getOrderById(orderId);
    }
    @Override
    @Transactional
    public void insert(Orders order) {
        // 计算运费：5件以内免费，超出每件加3元
        int totalQty = 0;
        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                totalQty += detail.getQuantity();
            }
        }
        double fee = totalQty > 5 ? (totalQty - 5) * 3.0 : 0.0;
        order.setShippingFee(fee);

        orderMapper.insert(order);
        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                detail.setOrderId(order.getOrderId());
                // 若厂家名称为空，从产品信息中自动填充
                if (detail.getManufacturerName() == null && detail.getProductId() != null) {
                    Product product = productMapper.getProductById(detail.getProductId());
                    if (product != null && product.getManufacturerName() != null) {
                        detail.setManufacturerName(product.getManufacturerName());
                    }
                }
                orderDetailMapper.insert(detail);
            }
        }
    }

    @Override
    @Transactional
    public void confirmSupply(String orderId) {
        // 获取订单明细，扣减每种商品的库存
        List<OrderDetail> details = orderDetailMapper.findByOrderId(orderId);
        if (details != null) {
            for (OrderDetail detail : details) {
                productMapper.deductStock(detail.getProductId(), detail.getQuantity());
            }
        }
        orderMapper.confirmSupply(orderId);
    }

    @Override
    public void updatePayment(String orderId, String paymentStatus, String paymentInfo) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setPaymentStatus(paymentStatus);
        orders.setPaymentInfo(paymentInfo);
        orderMapper.updatePayment(orders);
    }

    @Override
    public List<Orders> getCancelledOrders() {
        return orderMapper.getCancelledOrders();
    }

    @Override
    public void cancelOrder(String orderId) {
        orderMapper.cancelOrder(orderId);
    }
}
