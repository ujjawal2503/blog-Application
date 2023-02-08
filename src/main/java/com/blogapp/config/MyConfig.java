package com.blogapp.config;

import com.blogapp.services.impl.BlogUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public BlogUserServiceImpl blogUserServiceImpl(){
        return new BlogUserServiceImpl();
    }
}
