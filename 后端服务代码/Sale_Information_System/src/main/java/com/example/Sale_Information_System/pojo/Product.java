package com.example.Sale_Information_System.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productId; // 产品代码
    private String categoryCode; //商品分类编号
    private String manufacturerCode; //厂家代码
    private String description; //商品描述
    private Double unitPrice; //单价
    private Integer stockQuantity; //库存数量

    private String manufacturerName;// 关联查询




}
