package com.shopping.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderDto {
    private OrderDto() {}

    // 前端結帳請求
    public record CreateRequest(
        @NotBlank(message = "收件人姓名不能為空")
        String customerName,

        @NotBlank(message = "電話不能為空")
        String customerPhone,

        @NotBlank(message = "地址不能為空")
        String customerLocation,

        @NotEmpty(message = "購物車不能為空")
        @Valid // 觸發內部 List 的驗證
        List<OrderItemRequest> items
    ) {}

    public record OrderItemRequest(
        @NotNull(message = "商品ID不能為空")
        Integer productId,

        @NotNull
        @Min(value = 1, message = "數量至少要為 1")
        Integer quantity
    ) {}

    // 後端回傳的訂單資料 (含明細) ---
    public record Response(
        Integer id,
        String customerName,
        String customerPhone,
        String customerLocation,
        Integer totalAmount,
        String status,
        LocalDateTime createAt,
        List<OrderItemResponse> items // 巢狀結構
    ) {}
    
    public record OrderItemResponse(
        Integer productId,
        String productName,
        Integer price,
        Integer quantity
    ) {}
}