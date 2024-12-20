package com.sk.blogapp.response;

public record BlogErrorResponse(
    int status,
    String message
) {
    
}
