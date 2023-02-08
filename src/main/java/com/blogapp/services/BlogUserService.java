package com.blogapp.services;

import com.blogapp.payloads.BlogUserDto;

import java.util.List;

public interface BlogUserService {

    BlogUserDto createUser(BlogUserDto blogUserDto);
    BlogUserDto updateUser(BlogUserDto blogUserDto,Integer userId);
    BlogUserDto getUserById(Integer userId);
    List<BlogUserDto> getAllUsers();
    void deleteUserById(Integer userId);
}
