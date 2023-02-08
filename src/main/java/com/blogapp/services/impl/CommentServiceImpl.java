package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
   @Autowired
    CommentRepo commentRepo;
   @Autowired
    ModelMapper modelMapper;
   @Autowired
    PostRepo postRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId) {

        Comment comment = modelMapper.map(commentDto,Comment.class);
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","PostId",postId));
       comment.setPost(post);
        Comment commentTable = this.commentRepo.save(comment);
        return modelMapper.map(commentTable,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
    Comment res= this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","CommentId",commentId));
    this.commentRepo.delete(res);
    }
}
