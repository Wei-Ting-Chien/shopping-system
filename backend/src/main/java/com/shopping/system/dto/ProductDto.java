package com.shopping.system.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// Record: 欄位預設 Final, Thread 安全，適合用來表達 DTO / Value Object
public class ProductDto {
	// 防止被 New 出新的物件
	private ProductDto() {}
	// 創新的物件
	public record CreateRequest(
	        @NotBlank(message = "商品名稱不能為空")
	        String name,

	        @NotBlank(message = "分類不能為空")
	        String category,

	        @NotNull(message = "價格不能為空")
	        @Min(value = 0, message = "價格不能小於 0")
	        Integer price,

	        @NotNull(message = "庫存不能為空")
	        @Min(value = 0, message = "庫存不能小於 0")
	        Integer stock,
	        
	        String description,
	        
	        String imageUrl,
	        
	        Boolean status // 上架狀態
	    ) {}
	// 更新物件
	public record UpdateRequest(
	        @NotNull(message = "ID 不能為空")
	        Integer id,
	        String name,
	        String category,
	        @Min(0) Integer price,
	        @Min(0) Integer stock,
	        String description,
	        String imageUrl,
	        Boolean status
	    ) {}

	// 不可繼承 Class, 但可以實作 Interface
	public record Response(
	        Integer id,
	        String name,
	        String category,
	        Integer price,
	        Integer stock,
	        Boolean status,
	        String description,
	        String imageUrl,
	        LocalDateTime createAt // Create Time 好像不用給
	    ) {}
}
