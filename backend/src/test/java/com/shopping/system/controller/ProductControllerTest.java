package com.shopping.system.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.system.dto.ProductDto;
import com.shopping.system.service.ProductService;

@WebMvcTest(ProductController.class) // ✅ 只啟動 Web 層，不啟動整個 Spring Context (速度快)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // ✅ 用來模擬發送 HTTP 請求

    @MockitoBean
    private ProductService productService; // ✅ 模擬 Service 層 (我們只測 Controller，不真的去連 DB)

    @Autowired
    private ObjectMapper objectMapper; // 用來把 Java 物件轉成 JSON 字串

    @Test
    @DisplayName("測試：取得商品列表 (GET /api/products)")
    void testGetProducts() throws Exception {
        // 1. 準備假資料 (Mock Behavior)
        ProductDto.Response mockProduct = new ProductDto.Response(
            1, "iPhone 15", "手機", 30000, 10, true, "好手機", null, null
        );
        // 當 Service 被呼叫 getProducts 時，回傳上面的假資料
        when(productService.getProducts(any(), any())).thenReturn(List.of(mockProduct));

        // 2. 執行請求並驗證 (Perform & Verify)
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk()) // 期望狀態碼 200
                .andExpect(jsonPath("$[0].name").value("iPhone 15")) // 驗證 JSON 內容
                .andExpect(jsonPath("$[0].price").value(30000));
    }

    @Test
    @DisplayName("測試：取得單一商品 (GET /api/products/{id})")
    void testGetProductById() throws Exception {
        ProductDto.Response mockProduct = new ProductDto.Response(
            1, "iPhone 15", "手機", 30000, 10, true, "好手機", null, null
        );
        when(productService.getProductById(1)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("測試：新增商品成功 (POST /api/products)")
    void testCreateProduct() throws Exception {
        // 準備 Request DTO
        ProductDto.CreateRequest request = new ProductDto.CreateRequest(
            "MacBook", "筆電", 50000, 5, "M3", null, true
        );
        
        // 準備假的回傳 Response
        ProductDto.Response response = new ProductDto.Response(
            10, "MacBook", "筆電", 50000, 5, true, "M3", null, null
        );

        when(productService.createProduct(any(ProductDto.CreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // 把物件轉 JSON
                .andExpect(status().isCreated()) // 期望 201 Created
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("MacBook"));
    }

    @Test
    @DisplayName("測試：新增商品失敗 - 參數驗證 (400 Bad Request)")
    void testCreateProductValidationFail() throws Exception {
        // 故意傳一個價格是負數的請求
        ProductDto.CreateRequest badRequest = new ProductDto.CreateRequest(
            "", "", -100, -5, "", null, true
        );

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest()); // 期望被 GlobalExceptionHandler 攔截回傳 400
    }

    @Test
    @DisplayName("測試：刪除商品 (DELETE /api/products/{id})")
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent()); // 期望 204 No Content
    }
}