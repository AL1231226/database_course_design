package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.ProductService;
import com.example.Sale_Information_System.pojo.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        productService.insert(product);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable String id, @RequestBody Product product) {
        productService.update(product);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Map<String, Object>> getLowStock(@RequestParam(defaultValue = "50") Integer threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        Map<String, Object> result = new HashMap<>();
        result.put("products", products);
        result.put("count", products.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/{categoryCode}")
    public List<Product> getProductsByCategory(@PathVariable String categoryCode) {
        return productService.getProductsByCategory(categoryCode);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }
}
