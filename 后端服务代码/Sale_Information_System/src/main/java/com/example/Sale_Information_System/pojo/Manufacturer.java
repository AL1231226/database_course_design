package com.example.Sale_Information_System.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    private String manufacturerCode; //厂家代码
    private String manufacturerName; //厂家名称
    private String contactPerson;  //联系人
    private String contactPhone;  //联系电话
    private String address;  //


}
