package com.sk.blogapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sk.blogapp.models.Blog;
import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.BlogResponseWithAuthor;

public interface BlogRepository extends JpaRepository<Blog, String> {

    /**
     * Fetches all blogs with author information.
     *
     * @param pageable The pageable parameters.
     * @return A page of objects containing blog information and author information.
     */
    @Query("SELECT b.id, b.title, b.content, " +
            "a.id AS authorId, " +
            "a.name AS authorName, " +
            "a.email AS authorEmail, " +
            "a.imageUrl AS authorImageUrl " +
            "FROM Blog b " +
            "JOIN b.author a ")
    Page<Object[]> findAllBlogsWithPage(Pageable pageable);

    default List<BlogResponseWithAuthor> findAllBlogs(Pageable pageable) {
        Page<Object[]> result = findAllBlogsWithPage(pageable);
        Page<BlogResponseWithAuthor> blogResponse = result.map(
                r -> new BlogResponseWithAuthor(r[0].toString(), r[1].toString(), r[2].toString(),
                        new AuthorResponse(r[3].toString(), r[4].toString(), r[5].toString(), (String) r[6])));
        return blogResponse.getContent();
    }

    @Query("SELECT b.id, b.title, b.content, " +
            "a.id AS authorId, " +
            "a.name AS authorName, " +
            "a.email AS authorEmail, " +
            "a.imageUrl AS authorImageUrl " +
            "FROM Blog b " +
            "JOIN b.author a " +
            "WHERE b.id = :id")
    List<Object[]> fetchBlogById(String id);

    default BlogResponseWithAuthor findBlogById(String id) throws RuntimeException {
        List<Object[]> result = fetchBlogById(id);
        if (result.isEmpty()) {
            throw new RuntimeException("Blog not found");
        }

        return BlogResponseWithAuthor.toBlogResponse(result).get(0);
    }

}
