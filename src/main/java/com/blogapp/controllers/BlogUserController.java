package com.blogapp.controllers;

import com.blogapp.entities.BlogUser;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.BlogUserDto;
import com.blogapp.services.BlogUserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blogUsers")
public class BlogUserController {

    @Autowired
    BlogUserService blogUserService;

    //Post-creating user
    @PostMapping("/addUser")
    public ResponseEntity<BlogUserDto> addUser(@Valid @RequestBody BlogUserDto blogUserDto){
       BlogUserDto createdUser =this.blogUserService.createUser(blogUserDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //Put-Updating the details
    @PutMapping(value = "/updateUser/{userId}")
    public ResponseEntity<BlogUserDto> updateUser(@Valid @RequestBody BlogUserDto blogUserDto, @PathVariable ("userId") Integer userId){
        BlogUserDto updatedUser = this.blogUserService.updateUser(blogUserDto,userId);
        return ResponseEntity.ok(updatedUser);
    }

    //delete the details by id.
    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("userId") Integer userId){
        this.blogUserService.deleteUserById(userId);
        return new ResponseEntity(new ApiResponse("Successfully deleted",true),HttpStatus.OK);
    }

    //retrieve all user.
    @GetMapping("/allUser")
    public ResponseEntity<List <BlogUserDto>> getAllUser(){
       return ResponseEntity.ok(this.blogUserService.getAllUsers());
    }

    //retrieve user by id
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<BlogUserDto> getUserById(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(this.blogUserService.getUserById(userId));
    }

}
