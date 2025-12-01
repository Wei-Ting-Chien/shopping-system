package com.shopping.system.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.system.dto.OrderDto;
import com.shopping.system.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("測試：建立訂單成功 (POST /api/orders)")
    void testCreateOrder() throws Exception {
        // 1. 準備請求資料
        OrderDto.OrderItemRequest item = new OrderDto.OrderItemRequest(1, 2); // 買 ID=1 的商品 2 個
        OrderDto.CreateRequest request = new OrderDto.CreateRequest(
            "測試員", "0912345678", "台北市", List.of(item)
        );

        // 2. Mock Service 行為 (假設訂單 ID 產出為 100)
        when(orderService.createOrder(any(OrderDto.CreateRequest.class))).thenReturn(100);

        // 3. 執行測試
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // 預期 201
                .andExpect(content().string("100")); // 預期回傳 ID
    }

    @Test
    @DisplayName("測試：建立訂單失敗 - 購物車為空 (400 Bad Request)")
    void testCreateOrderEmptyCart() throws Exception {
        // 購物車是空的 list
        OrderDto.CreateRequest request = new OrderDto.CreateRequest(
            "測試員", "0912345678", "台北市", List.of() 
        );

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 驗證 @NotEmpty 生效
    }
}