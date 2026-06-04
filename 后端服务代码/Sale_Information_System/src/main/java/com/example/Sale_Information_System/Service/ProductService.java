package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(String productId);


}
