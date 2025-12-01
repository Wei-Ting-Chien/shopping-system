package com.shopping.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.system.dto.ProductDto;
import com.shopping.system.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController // API Entry + Response is Json
@Tag(name = "商品管理 API", description = "提供商品的增刪改查功能") // 美化 Swagger API
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping // Get Product 查詢
    @Operation(summary = "查詢商品列表", description = "支援分類與關鍵字篩選")
    public List<ProductDto.Response> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category
    ) {
        return productService.getProducts(keyword, category);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "查詢單一商品", description = "根據 ID 取得商品詳細資訊")
    public ResponseEntity<ProductDto.Response> getProduct(@PathVariable Integer id) {
        ProductDto.Response product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build(); // 404 Error Build
        }
    }
    
    @PostMapping
    @Operation(summary = "新增商品", description = "建立新商品，需帶入庫存與價格")
    public ResponseEntity<ProductDto.Response> createProduct(@Valid @RequestBody ProductDto.CreateRequest request) {
        ProductDto.Response response = productService.createProduct(request);
        
        // 201 Status Code
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto.Response> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductDto.UpdateRequest request) {
        
        // 確保 PathVariable 跟 Body 裡的 ID 一致
        if (!id.equals(request.id())) {
            return ResponseEntity.badRequest().build();
        }

        ProductDto.Response updatedProduct = productService.updateProduct(id, request);
        
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content (標準刪除成功回應)
    }
}