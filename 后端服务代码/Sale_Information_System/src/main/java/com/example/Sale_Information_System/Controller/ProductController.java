package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.ProductService;
import com.example.Sale_Information_System.pojo.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @RequestMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @RequestMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

}
