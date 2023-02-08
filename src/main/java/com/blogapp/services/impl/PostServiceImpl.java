package com.blogapp.services.impl;

import com.blogapp.entities.BlogUser;
import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.BlogUserRepo;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;
    @Autowired
    BlogUserRepo blogUserRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CommentRepo commentRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer blogUserId, Integer categoryId) {
        BlogUser blogUser = this.blogUserRepo.findById(blogUserId).orElseThrow(() -> new ResourceNotFoundException("user", "id", blogUserId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("userId", "id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setBlogUser(blogUser);
        Post createdPost = postRepo.save(post);
        return modelMapper.map(createdPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", "id", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        postRepo.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", "id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():
                 Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePostList = this.postRepo.findAll(p);
        List<Post> postList = pagePostList.getContent();
        postList.stream().forEach(e->{
            List<Comment> comments=this.commentRepo.findByPost(e);
            Set<Comment> postSet =new HashSet<Comment>();
            postSet =comments.stream().collect(Collectors.toSet());
            e.setComments(postSet);
        });



        List<PostDto> postDtoList = postList.stream().map(e -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtoList(postDtoList);
        postResponse.setPageNumber(pagePostList.getNumber());
        postResponse.setPageSize(pagePostList.getSize());
        postResponse.setTotalPages(pagePostList.getTotalPages());
        postResponse.setTotalElements((int) pagePostList.getTotalElements());
        postResponse.setLastPage(pagePostList.isLast());
        return postResponse;
    }

    @Override
    public void deletePostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", "id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));
        List<Post> list = this.postRepo.findByCategory(category);
        return list.stream().map(e -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostByBlogUser(Integer blogUserId) {
        BlogUser blogUser = this.blogUserRepo.findById(blogUserId).orElseThrow(() -> new ResourceNotFoundException("blogUser", "blogId", blogUserId));
        List<Post> list = this.postRepo.findByBlogUser(blogUser);
        return list.stream().map(e -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostByKeyword(String keyword) {
        List<Post> list = this.postRepo.findByTitleContaining(keyword);
        return list.stream().map(e -> modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
    }
}
