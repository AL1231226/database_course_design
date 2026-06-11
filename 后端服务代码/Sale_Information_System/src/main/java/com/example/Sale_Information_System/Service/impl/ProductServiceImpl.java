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

    @Override
    public void insert(Product product) {
        if (product.getProductId() == null || product.getProductId().isBlank()) {
            product.setProductId(generateProductId());
        }
        productMapper.insert(product);

    }

    private String generateProductId() {
        String maxId = productMapper.getMaxProductId();
        if (maxId == null) {
            return "P001";
        }
        int num = Integer.parseInt(maxId.replaceAll("\\D", ""));
        return String.format("P%03d", num + 1);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);

    }

    @Override
    public void delete(String productId) {
        productMapper.delete(productId);
    }

    @Override
    public List<Product> getLowStockProducts(Integer threshold) {
        return productMapper.getLowStockProducts(threshold);
    }

    @Override
    public List<Product> getProductsByCategory(String categoryCode) {
        return productMapper.getProductsByCategory(categoryCode);
    }

    @Override
    public List<String> getAllCategories() {
        return productMapper.getAllCategories();
    }

}
