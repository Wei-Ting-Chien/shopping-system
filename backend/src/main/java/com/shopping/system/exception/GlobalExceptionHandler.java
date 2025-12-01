package com.shopping.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shopping.system.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 自訂的 Runtime Exception
	@ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ApiResponse<Void>> handleShoppingException(ShoppingException e) {
        // 使用 Exception 內的 status Code
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.error(e.getStatus().value(), e.getMessage()));
    }

    // DTO Verification ( @NotNull, @Min )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, errorMessage));
    }
    
    // OTHER Error message
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        e.printStackTrace(); // 印在後台 log
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "系統發生未預期錯誤，請聯繫管理員"));
    }
}