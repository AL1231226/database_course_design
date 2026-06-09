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
    @RequestMapping("/unpaid")
    public List<Orders> getUnpaidOrders() {
        return orderService.getUnpaidOrders();
    }
    @RequestMapping("/unshipped")
    public List<Orders> getUnshippedOrders() {
        return orderService.getUnshippedOrders();
    }
    @RequestMapping("/completed")
    public List<Orders> getCompletedOrders() {
        return orderService.getCompletetedOrders() ;
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmSupply(@PathVariable String id) {
        orderService.confirmSupply(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Void> updatePayment(@PathVariable String id, @RequestBody Orders orders) {
        orderService.updatePayment(id, orders.getPaymentStatus(), orders.getPaymentInfo());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/cancelled")
    public List<Orders> getCancelledOrders() {
        return orderService.getCancelledOrders();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }


}
