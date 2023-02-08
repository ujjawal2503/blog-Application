package com.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {

    private Integer commentId;
    @NotEmpty
    @Size(min=10,message = "Name should be of minimum 10 chars")
    private String content;
}
