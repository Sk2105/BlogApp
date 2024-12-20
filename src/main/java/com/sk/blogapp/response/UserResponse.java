package com.sk.blogapp.response;

import com.sk.blogapp.models.User;

public record UserResponse(
        String id,
        String name,
        String email,
        String imageUrl) {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(),user.getImageUrl());
    }

}
