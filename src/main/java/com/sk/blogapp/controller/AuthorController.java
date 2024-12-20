package com.sk.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.BlogResponseWithAuthor;
import com.sk.blogapp.services.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorResponse> getAllAuthors(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return authorService.getAllAuthors(pageable);
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthor(@PathVariable String id) {
        return authorService.getAuthor(id);
    }

    @GetMapping("/{id}/blogs")
    public List<BlogResponseWithAuthor> getAuthorBlogs(@PathVariable String id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return authorService.findBlogsByAuthorId(id, pageable);
    }

   

}
