package com.example.Sale_Information_System.Service;

import com.example.Sale_Information_System.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(String productId);
    void insert(Product product);
    void update(Product product);
    void delete(String productId);
    List<Product> getLowStockProducts(Integer threshold);
    List<Product> getProductsByCategory(String categoryCode);
    List<String> getAllCategories();
}
