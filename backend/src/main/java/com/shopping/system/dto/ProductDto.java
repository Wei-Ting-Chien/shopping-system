package com.shopping.system.dto;

import java.time.LocalDateTime;

public class ProductDto {
	// 防止被 New 出新的物件
	private ProductDto() {}
	// 欄位預設 Final, Thread 安全，適合用來表達 DTO / Value Object
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
