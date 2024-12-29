package com.sk.blogapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sk.blogapp.models.User;
import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.BlogResponseWithAuthor;

public interface UserRepository extends JpaRepository<User, String> {

        User findByEmail(String email);

        @Query("SELECT u.id, u.name, u.email, u.imageUrl as image_url " +
                        "FROM User u")
        Page<Object[]> fetchAllAuthors(Pageable pageable);

        default List<AuthorResponse> findAllAuthors(Pageable pageable) {
                Page<Object[]> authors = fetchAllAuthors(pageable);
                return authors.map(r -> new AuthorResponse(r[0].toString(), r[1].toString(), r[2].toString(),
                                (String) r[3])).getContent();
        }

        @Query("SELECT u.id, u.name, u.email, u.imageUrl as image_url " +
                        "FROM User u " +
                        "WHERE u.id = :id")
        List<Object[]> fetchAuthorById(String id);

        default AuthorResponse findAuthorById(String id) throws RuntimeException {
                List<Object[]> authorData = fetchAuthorById(id);
                if (authorData.isEmpty()) {
                        throw new RuntimeException("Author not found");
                }
                return new AuthorResponse(
                                authorData.get(0)[0].toString(),
                                authorData.get(0)[1].toString(),
                                authorData.get(0)[2].toString(),
                                (String) authorData.get(0)[3]);
        }

        @Query("SELECT b.id, b.title, b.content " +
                        "FROM Blog b " +
                        "WHERE b.author.id = :id")
        Page<Object[]> fetchBlogsByAuthorId(String id, Pageable pageable);

        /**
         * Finds blogs by author ID.
         *
         * @param id The ID of the author.
         * @return A list of BlogResponseWithAuthor objects.
         * @throws RuntimeException if the author ID is invalid or not found.
         */
        default Page<BlogResponseWithAuthor> findBlogsByAuthorId(String id, Pageable pageable) throws RuntimeException {
                // Fetch the author details using the author ID.
                AuthorResponse author = findAuthorById(id);
                if (author == null) {
                        throw new RuntimeException("Invalid author id");
                }

                // Fetch the blog data associated with the author ID.
                Page<Object[]> blogData = fetchBlogsByAuthorId(id, pageable);

                // Convert the blog data to a list of BlogResponseWithAuthor objects.
                return blogData.map(
                                b -> new BlogResponseWithAuthor(
                                                b[0].toString(),
                                                b[1].toString(),
                                                b[2].toString(),
                                                author));
        }

}
