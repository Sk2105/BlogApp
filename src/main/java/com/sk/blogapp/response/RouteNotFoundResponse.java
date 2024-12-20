package com.sk.blogapp.response;

public record RouteNotFoundResponse(
    int status,
    String message,
    String route
) {
    
}
