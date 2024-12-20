package com.sk.blogapp.response;

public record MethodNotAllowedResponse(
        int status,
        String message,
        String method) {

}
