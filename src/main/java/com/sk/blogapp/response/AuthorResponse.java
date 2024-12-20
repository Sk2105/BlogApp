package com.sk.blogapp.response;

public record AuthorResponse(
    String id,
    String name,
    String email,
    String imageUrl
) {
    
}
