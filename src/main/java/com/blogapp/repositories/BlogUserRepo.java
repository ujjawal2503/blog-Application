package com.blogapp.repositories;

import com.blogapp.entities.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserRepo extends JpaRepository<BlogUser,Integer> {
}
