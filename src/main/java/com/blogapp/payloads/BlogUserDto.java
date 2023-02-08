package com.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class BlogUserDto {

    private Integer id;
    @NotEmpty
    @Size(min=4,message = "Name should be of minimum 4 chars")
    private String name;
    @Email
    private String email;
    @NotEmpty
    @Size(min=3,max = 10 ,message = "password should be between 3 chars to 10 chars")
    private String password;
    @NotEmpty
    @Size(min=10,max = 200 ,message = "About box should be between 10 chars to 200 chars")
    private String about;
}
