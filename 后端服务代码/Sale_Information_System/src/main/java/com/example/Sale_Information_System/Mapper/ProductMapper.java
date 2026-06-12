package com.example.Sale_Information_System.Mapper;

import com.example.Sale_Information_System.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> getAllProducts();
    Product getProductById(String productId);
    void insert(Product product);
    void update(Product product);
    void delete(String productId);
    void deductStock(@Param("productId") String productId, @Param("quantity") Integer quantity);
    void restoreStock(@Param("productId") String productId, @Param("quantity") Integer quantity);
    List<Product> getLowStockProducts(@Param("threshold") Integer threshold);
    List<Product> getProductsByCategory(String categoryCode);
    List<String> getAllCategories();
    String getMaxProductId();

}
