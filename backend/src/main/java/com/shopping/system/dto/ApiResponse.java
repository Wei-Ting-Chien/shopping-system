package com.shopping.system.dto;

// Record (Default in Java17)，自動產生 Getter & Constructor
// <T> 是泛型， data 可以是任何東西 (Product, Order, String...)
public record ApiResponse<T>(
    int status,      // Status Code
    String message,  // Response Message
    T data           // True Data
) {
    // Success Method
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    // Error Method
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}