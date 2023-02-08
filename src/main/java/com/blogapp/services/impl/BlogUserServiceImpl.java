package com.blogapp.services.impl;

import com.blogapp.entities.BlogUser;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.BlogUserDto;
import com.blogapp.repositories.BlogUserRepo;
import com.blogapp.services.BlogUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class BlogUserServiceImpl implements BlogUserService {

    @Autowired
    BlogUserRepo blogUserRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BlogUserDto createUser(BlogUserDto blogUserDto) {
        BlogUser blogUser =  dtoToUser(blogUserDto);
        blogUserRepo.save(blogUser);
        return userToDto(blogUser);
    }

    @Override
    public BlogUserDto updateUser(BlogUserDto blogUserDto, Integer userId) {
        BlogUser blogUser = this.blogUserRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
        blogUser.setPassword(blogUserDto.getPassword());
        blogUser.setAbout(blogUserDto.getAbout());
        blogUser.setName(blogUserDto.getName());
        blogUser.setEmail(blogUserDto.getEmail());
        BlogUser updatedBlogUser = blogUserRepo.save(blogUser);
        return userToDto(updatedBlogUser);
    }

    @Override
    public BlogUserDto getUserById(Integer userId) {
        BlogUser blogUser = this.blogUserRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
        return userToDto(blogUser);
    }

    @Override
    public List<BlogUserDto> getAllUsers() {
        List<BlogUser> list = this.blogUserRepo.findAll();
        List<BlogUserDto> blogUserDtos =list.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return blogUserDtos;
    }

    @Override
    public void deleteUserById(Integer userId) {
        BlogUser blogUser = this.blogUserRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
        this.blogUserRepo.delete(blogUser);
    }

    public BlogUser dtoToUser(BlogUserDto blogUserDto){
        /* BlogUser blogUser =new BlogUser();
        blogUser.setId(blogUserDto.getId());
        blogUser.setName(blogUserDto.getName());
        blogUser.setAbout(blogUserDto.getAbout());
        blogUser.setEmail(blogUserDto.getEmail());
        blogUser.setPassword(blogUserDto.getPassword());*/

        BlogUser blogUser = this.modelMapper.map(blogUserDto,BlogUser.class);
        return  blogUser;
    }

    public BlogUserDto userToDto(BlogUser blogUser){
      /*  BlogUserDto blogUserDto =new BlogUserDto();
        blogUserDto.setId(blogUser.getId());
        blogUserDto.setName(blogUser.getName());
        blogUserDto.setEmail(blogUser.getEmail());
        blogUserDto.setAbout(blogUser.getAbout());
        blogUserDto.setPassword(blogUser.getPassword());*/
        BlogUserDto blogUserDto = this.modelMapper.map(blogUser,BlogUserDto.class);
        return blogUserDto;
    }
}
