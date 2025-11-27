package com.shopping.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.system.dto.ProductDto;
import com.shopping.system.service.ProductService;

@RestController // API Entry + Response is Json
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping //Get
    public List<ProductDto.Response> getAllProducts() {
        return productService.getProducts();
    }
}