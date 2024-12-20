package com.sk.blogapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blogapp.dto.BlogDTO;
import com.sk.blogapp.response.BlogResponseWithAuthor;
import com.sk.blogapp.response.MessageResponse;
import com.sk.blogapp.services.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<?> getAllBlogs(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) throws RuntimeException {
        List<BlogResponseWithAuthor> blogPage = blogService.getPaged(page, size);
        return ResponseEntity.ok(blogPage);
    }


    @GetMapping("/{id}")
    public BlogResponseWithAuthor getBlog(@PathVariable String id) throws RuntimeException {
        return blogService.getBlog(id);
    }


     @PostMapping
    public BlogResponseWithAuthor createBlog(@RequestBody BlogDTO blogDTO) throws RuntimeException {
        return blogService.createBlog(blogDTO);
    }

    @PutMapping("/{id}")
    public BlogResponseWithAuthor updateBlog(@PathVariable String id, @RequestBody BlogDTO blogDTO)
            throws RuntimeException {
        return blogService.updateBlog(id, blogDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) throws RuntimeException {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(new MessageResponse("Blog deleted successfully", HttpStatus.OK.value()));
    }
   

}
