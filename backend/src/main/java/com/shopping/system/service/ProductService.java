package com.shopping.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.system.dto.ProductDto;
import com.shopping.system.mapper.ProductMapper;
import com.shopping.system.model.Product;

@Service // Bean Component
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    // 接收 Keyword 與 Category 參數，用來篩選呈現的產品
    public List<ProductDto.Response> getProducts(String keyword, String category) {
        // 用來存放產品的
        List<Product> products = productMapper.selectByCondition(keyword, category);

        // 2. 轉換 Entity -> DTO (使用 Java Stream API)
        return products.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
    // 根據 ID 查詢產品
    public ProductDto.Response getProductById(Integer id) {
        Product product = productMapper.selectById(id);
        
        // 如果查不到，回傳 null (Controller 會處理成 404)
        if (product == null) {
            return null;
        }
        
        return toResponse(product);
    }
    
    // 新增商品，同時確保資料寫入一致性
    @Transactional
    public ProductDto.Response createProduct(ProductDto.CreateRequest request) {
        // DTO 轉 Entity
        Product product = new Product();
        product.setName(request.name());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setDescription(request.description());
        product.setImageUrl(request.imageUrl());
        product.setStatus(request.status() != null ? request.status() : true);//預設 True
        
        // 存入 Database
        productMapper.insert(product);
        
        // 轉回 Response DTO
        return toResponse(product);
    }
    
    @Transactional
    public ProductDto.Response updateProduct(Integer id, ProductDto.UpdateRequest request) {
        // 確認商品是否存在
        Product product = productMapper.selectById(id);
        if (product == null) {
            return null; // 或者拋出 Exception
        }

        // 因為 Mapper 用 if, 所以要填入原本的值
        // MyBatis 會處理 Null 的問題，所以直接把 DTO Value 丟過去
        product.setName(request.name());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setDescription(request.description());
        product.setImageUrl(request.imageUrl());
        product.setStatus(request.status());
        
        productMapper.update(product);

        // 確認 DB 更新
        return toResponse(productMapper.selectById(id));
    }


    @Transactional
    public void deleteProduct(Integer id) {
        productMapper.deleteById(id);
    }
    
    // 把 Entity 轉 DTO
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