package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> getAllProducts();
    Product getProductById(String productId);
    void insert(Product product);
    void update(Product product);
    void delete(String productId);

}
