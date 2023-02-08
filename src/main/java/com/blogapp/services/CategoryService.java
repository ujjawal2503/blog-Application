package com.blogapp.services;

import com.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //update
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    //create
    public CategoryDto addCategory(CategoryDto categoryDto);
    //get all
    public List<CategoryDto> getAllCategory();
    //get
    public CategoryDto getCategoryById(Integer categoryId);
    //delete
    public void deleteCategory(Integer categoryId);
}
