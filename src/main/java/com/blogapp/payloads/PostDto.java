package com.blogapp.payloads;

import com.blogapp.entities.BlogUser;
import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private CategoryDto category;
    private BlogUserDto blogUser;
    private Set<CommentDto> comment = new HashSet<>();
}
