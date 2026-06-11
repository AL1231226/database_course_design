package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin findByUsername(String username);
    void insert(Admin admin);
}
