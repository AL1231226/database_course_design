package com.example.Sale_Information_System.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {
    private String orderId; //订单号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate; //下单时间
    private String customerId;  //客户代码
    private String shippingRequirements; //配送要求
    private String supplyStatus; //供应状态
    private Date shippingDate; //运输日期
    private Double weight; //重量
    private Double shippingFee; //运费
    private String paymentInfo; //付款清单
    private String paymentStatus; //付款状态
    private Boolean isCancelled; //是否已取消
    private String adminFeedback; //管理员发货反馈
    private Double totalAmount; // 总金额（含运费）

    private String customerName; // 关联查询
    private List<OrderDetail> details; // 订单明细


}
