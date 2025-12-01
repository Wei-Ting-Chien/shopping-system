package com.shopping.system.exception;

import org.springframework.http.HttpStatus;

// 繼承 RuntimeException，這樣我們才能在 Service 層直接 throw，不用 try-catch
public class ShoppingException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus status;

    // Default 400 Bad Request
    public ShoppingException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    // 用於自訂 Status Code
    public ShoppingException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}