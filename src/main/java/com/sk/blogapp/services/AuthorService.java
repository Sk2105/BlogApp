package com.sk.blogapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sk.blogapp.repository.UserRepository;
import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.BlogResponseWithAuthor;

@Service
public class AuthorService {

    @Autowired
    private UserRepository userRepository;


    public List<AuthorResponse> getAllAuthors(Pageable pageable) throws RuntimeException {
        return userRepository.findAllAuthors(pageable);
    }

    public AuthorResponse getAuthor(String id) throws RuntimeException {
        return userRepository.findAuthorById(id);
    }

    /**
     * Finds blogs by author ID.
     * 
     * @param id The ID of the author.
     * @return A list of BlogResponseWithAuthor objects.
     * @throws RuntimeException if the author ID is invalid or not found.
     */
    public List<BlogResponseWithAuthor> findBlogsByAuthorId(String id, Pageable pageable) throws RuntimeException {
        return userRepository.findBlogsByAuthorId(id, pageable).getContent();
    }


}
