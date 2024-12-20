package com.sk.blogapp.response;

public record ErrorResponse(
    int code,
    String error
) {
    
}
