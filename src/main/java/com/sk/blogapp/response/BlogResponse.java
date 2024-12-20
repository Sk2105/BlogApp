package com.sk.blogapp.response;

import java.util.List;

public record BlogResponse(
        String id,
        String title,
        String content) {

    public static List<BlogResponse> toBlogResponse(List<Object[]> blogList) {
        return blogList.stream()
                .map(r -> new BlogResponse(r[0].toString(), r[1].toString(), r[2].toString()))
                .toList();
    }

}
