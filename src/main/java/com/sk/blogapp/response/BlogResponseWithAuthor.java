package com.sk.blogapp.response;

import java.util.List;

public record BlogResponseWithAuthor(
        String id,
        String title,
        String content,
        AuthorResponse author) {

    public static List<BlogResponseWithAuthor> toBlogResponse(List<Object[]> result) {
        return result.stream()
                .map(r -> new BlogResponseWithAuthor(
                        r[0].toString(),
                        r[1].toString(),
                        r[2].toString(),
                        new AuthorResponse((String) r[3].toString(), (String) r[4].toString(),
                                (String) r[5].toString(),(String) r[6])))
                .toList();
    }

}
