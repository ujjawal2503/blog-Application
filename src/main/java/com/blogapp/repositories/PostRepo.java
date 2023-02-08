package com.blogapp.repositories;

import com.blogapp.entities.BlogUser;
import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByCategory(Category category);

    List<Post> findByBlogUser(BlogUser blogUser);

    List<Post> findByTitleContaining(String title);

}
