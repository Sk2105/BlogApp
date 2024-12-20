package com.sk.blogapp.response;

public record UserNotFoundResponse(
    int status,
    String message
) {
} 