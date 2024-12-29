package com.sk.blogapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sk.blogapp.dto.BlogDTO;
import com.sk.blogapp.models.Blog;
import com.sk.blogapp.models.User;
import com.sk.blogapp.repository.BlogRepository;
import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.BlogResponseWithAuthor;
import com.sk.blogapp.response.UserResponse;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;

    public List<BlogResponseWithAuthor> getPaged(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return blogRepository.findAllBlogs(pageable);
    }

    public BlogResponseWithAuthor getBlog(String id) throws RuntimeException {
        return blogRepository.findBlogById(id);
    }

    public BlogResponseWithAuthor createBlog(BlogDTO blogDTO) throws RuntimeException {
        var userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userdetails == null) {
            throw new RuntimeException("User not found");
        }

        User user = userService.getUser(userdetails.getUsername());
        UserResponse userResponse = userService.getMe();

        Blog blog = new Blog();
        blog.setTitle(blogDTO.title());
        blog.setContent(blogDTO.content());
        blog.setAuthor(user);
        user.getBlogs().add(blog);

        blogRepository.save(blog);

        BlogResponseWithAuthor blogResponse = new BlogResponseWithAuthor(blog.getId(), blog.getTitle(),
                blog.getContent(),
                new AuthorResponse(
                        userResponse.id(),
                        userResponse.name(),
                        userResponse.email(),
                        userResponse.imageUrl()));
        return blogResponse;
    }

    public BlogResponseWithAuthor updateBlog(String id, BlogDTO blogDTO) throws RuntimeException {
        var userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userdetails == null) {
            throw new RuntimeException("User not found");
        }

        User user = userService.getUser(userdetails.getUsername());
        UserResponse userResponse = userService.getMe();

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this blog");
        }

        blog.setTitle(blogDTO.title());
        blog.setContent(blogDTO.content());

        blogRepository.save(blog);

        BlogResponseWithAuthor blogResponse = new BlogResponseWithAuthor(blog.getId(), blog.getTitle(),
                blog.getContent(),
                new AuthorResponse(
                        userResponse.id(),
                        userResponse.name(),
                        userResponse.email(),
                        userResponse.imageUrl()));
        return blogResponse;
    }

    public void deleteBlog(String id) throws RuntimeException {
        var userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userdetails == null) {
            throw new RuntimeException("User not found");
        }

        User user = userService.getUser(userdetails.getUsername());

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        if (!blog.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this blog");
        }

        blogRepository.delete(blog);
    }

}
