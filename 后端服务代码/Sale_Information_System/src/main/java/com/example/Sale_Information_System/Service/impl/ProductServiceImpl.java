package com.example.Sale_Information_System.Service.impl;



import com.example.Sale_Information_System.Mapper.ProductMapper;
import com.example.Sale_Information_System.Service.ProductService;
import com.example.Sale_Information_System.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public Product getProductById(String productId) {
        return productMapper.getProductById(productId);
    }


}
