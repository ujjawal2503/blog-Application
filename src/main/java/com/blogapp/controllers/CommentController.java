package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CommentDto;
import com.blogapp.services.CategoryService;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
                                                 @PathVariable("postId") Integer postId){
        CommentDto commentDtoRes = this.commentService.createComment(commentDto,postId);
        return ResponseEntity.ok(commentDtoRes);

    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId")Integer commentId){
        //ResponseEntity res;
        this.commentService.deleteComment(commentId);
        return   ResponseEntity.ok(new ApiResponse(   "comment deleted successfully",true));
    }
}
