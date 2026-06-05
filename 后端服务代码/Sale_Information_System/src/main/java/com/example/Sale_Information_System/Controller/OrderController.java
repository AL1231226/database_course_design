package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.OrderService;
import com.example.Sale_Information_System.pojo.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/orders")

public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @RequestMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }
    @RequestMapping("/{id}")
    public Orders getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody Orders orders) {
        orderService.insert(orders);
        return ResponseEntity.ok().build();
    }

}
