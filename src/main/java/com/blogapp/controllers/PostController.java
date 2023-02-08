package com.blogapp.controllers;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/create-post")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        PostDto createdPostDto = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    //get post by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByBlogUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(this.postService.getAllPostByBlogUser(userId));
    }

    //get post by Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(this.postService.getAllPostByCategory(categoryId));
    }

    //update post
    @PutMapping("update-post/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
        PostDto updatedPostDto = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPostDto);
    }

    //fetch post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
        PostDto fetchedPostDto = postService.getPostById(postId);
        return ResponseEntity.ok(fetchedPostDto);
    }

    //fetch all post
    //Here Pagination is implemented along with sorting the content
    @GetMapping("/get-all-post")
    public ResponseEntity<PostResponse> getPostById(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        return ResponseEntity.ok(this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir));
    }

    //fetch post by content by string
    @GetMapping("/get-by-string/{keyword}")
    public ResponseEntity<List<PostDto>> getPostByKeyword(@PathVariable("keyword") String keyword) {
        List<PostDto> fetchedPostDto = postService.getAllPostByKeyword(keyword);
        return ResponseEntity.ok(fetchedPostDto);
    }

    //delete post by id
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
        this.postService.deletePostById(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted Successfully", true), HttpStatus.OK);
    }


    /*
    * Below two controller are not working need some debugging for getting the exact root cause.
    * */
    //Uploading post image
    @PostMapping(value = "/upload-image/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
                                               @PathVariable Integer postId) throws IOException {
        String fileName = this.fileService.uploadImage(path, image);
        PostDto postDto = this.postService.getPostById(postId);
        System.out.println("filename--------------------" + fileName);
        postDto.setImageName(fileName);
        System.out.println("filename--------------------" + postDto.getImageName());
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        System.out.println("filename--------------------" + updatePost.getImageName());
        return ResponseEntity.ok(updatePost);
    }
    //Method to serve the files
    @GetMapping(value = "/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
