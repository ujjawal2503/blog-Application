package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
   @Autowired
    CategoryRepo categoryRepo;
   @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("userId","id",categoryId));
        category.setCategoryDesc(categoryDto.getCategoryDesc());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryRepo.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category =modelMapper.map(categoryDto,Category.class);
        categoryRepo.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> list = categoryRepo.findAll();
        List<CategoryDto> listRes = list.stream().map(e->modelMapper.map(e,CategoryDto.class)).collect(Collectors.toList());
        return listRes;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("user","id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("user","id",categoryId));
        categoryRepo.delete(category);
    }
}
