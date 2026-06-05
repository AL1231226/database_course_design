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
    public Orders getOrderById(String orderId) {
        return orderMapper.getOrderById(orderId);
    }
    @Override
    @Transactional
    public void insert(Orders order) {
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
}
