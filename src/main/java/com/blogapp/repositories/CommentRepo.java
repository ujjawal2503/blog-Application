package com.blogapp.repositories;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findByPost(Post post);
}
