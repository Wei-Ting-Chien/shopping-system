package com.shopping.system.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopping.system.dto.ProductDto;
import com.shopping.system.mapper.ProductMapper;
import com.shopping.system.model.Product;

@Service // Bean Component
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    // 取得商品列表 API 的邏輯
    public List<ProductDto.Response> getProducts() {
        // 1. 從 DB 拿 Entity
        List<Product> products = productMapper.selectAll();

        // 2. 轉換 Entity -> DTO (使用 Java Stream API)
        return products.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
    
    // 輔助方法：把 Entity 轉成 DTO
    private ProductDto.Response toResponse(Product product) {
        return new ProductDto.Response(
        		product.getId(),
        		product.getName(),
        		product.getCategory(),
        		product.getPrice(),
        		product.getStock(),
        		product.getStatus(),
        		product.getDescription(),
        		product.getImageUrl(),
        		product.getCreateAt()
        );
    }
}