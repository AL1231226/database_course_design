package com.example.Sale_Information_System.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Integer detailId; // 订单明细编号
    private String orderId; // 订单号
    private String productId; // 产品代码
    private  String manufacturerName;// 厂家编号
    private Integer quantity; // 数量
    private Double unitPrice; // 单价
    private Double totalAmount; // 总金额

    private String productDescription; // 关联查询



}
