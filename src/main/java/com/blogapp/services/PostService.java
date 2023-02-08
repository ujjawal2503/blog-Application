package com.blogapp.services;

import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //add the post detail
    public PostDto createPost(PostDto postDto,Integer blogUserId,Integer categoryId);
    //update the post detail
    public PostDto updatePost(PostDto postDto,Integer postId);
    //fetch the post by id
    public PostDto getPostById(Integer postId);
    //fetch all the post
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize, String sortBy, String sortDir);
    //delete the post
    public void deletePostById(Integer postId);
    //get all post by Category
    public List<PostDto> getAllPostByCategory(Integer categoryId);
    //get all post by User
    public List<PostDto> getAllPostByBlogUser(Integer blogUserId);
    //search post by keyword
    public List<PostDto> getAllPostByKeyword(String keyword);
}
