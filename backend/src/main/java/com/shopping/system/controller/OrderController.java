package com.shopping.system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopping.system.dto.OrderDto;
import com.shopping.system.model.Order;
import com.shopping.system.model.OrderItem;
import com.shopping.system.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "訂單管理 API", description = "處理結帳與訂單查詢")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. 建立訂單 (結帳)
    @PostMapping
    @Operation(summary = "建立新訂單", description = "傳入收件人資訊與購買商品清單")
    public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderDto.CreateRequest request) {
        Integer orderId = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    // 2. 查詢所有訂單
    @GetMapping
    @Operation(summary = "查詢訂單列表", description = "回傳所有歷史訂單 (包含明細)")
    public List<OrderDto.Response> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        // 轉換 Entity -> DTO
        return orders.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // 3. 查詢單筆訂單
    @GetMapping("/{id}")
    @Operation(summary = "查詢訂單詳情", description = "根據 ID 查詢訂單內容")
    public ResponseEntity<OrderDto.Response> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(toResponse(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- 輔助方法：將 Entity 轉成 DTO ---
    private OrderDto.Response toResponse(Order order) {
        // 1. 先把明細 (OrderItem Entity) 轉成 DTO
        List<OrderDto.OrderItemResponse> itemDtos = null;
        if (order.getOrderItems() != null) {
            itemDtos = order.getOrderItems().stream()
                .map(item -> new OrderDto.OrderItemResponse(
                        item.getProductId(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity()))
                .collect(Collectors.toList());
        }

        // 2. 組裝主檔
        return new OrderDto.Response(
            order.getId(),
            order.getCustomerName(),
            order.getCustomerPhone(),
            order.getCustomerLocation(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getCreateAt(),
            itemDtos
        );
    }
}