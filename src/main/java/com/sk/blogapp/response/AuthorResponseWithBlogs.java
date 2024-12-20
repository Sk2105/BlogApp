package com.sk.blogapp.response;

import java.util.List;

public record AuthorResponseWithBlogs(
        String id,
        String name,
        String email,
        List<BlogResponse> blogs) {

    public static  List<AuthorResponseWithBlogs> toAuthorResponseWithBlogs(List<Object[]> result, List<Object[]> blogList) {
        return result.stream().map(r -> new AuthorResponseWithBlogs(r[0].toString(), r[1].toString(), r[2].toString(),
                BlogResponse.toBlogResponse(blogList))).toList();
    }

}
