package com.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;
    @NotEmpty
    @Size(min=4,message = "Name should be of minimum 4 chars")
    private String categoryTitle;
    @NotEmpty
    @Size(min=4,message = "Name should be of minimum 4 chars")
    private String categoryDesc;
}
