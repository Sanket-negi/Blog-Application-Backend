package com.sanket.blog_app_apis.services.impl;

import com.sanket.blog_app_apis.entities.Category;
import com.sanket.blog_app_apis.exceptions.ResourceNotFoundException;
import com.sanket.blog_app_apis.payloads.categoryDTO;
import com.sanket.blog_app_apis.repositories.CategoryRepo;
import com.sanket.blog_app_apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public categoryDTO createCategory(categoryDTO categorydto) {
        Category cat = this.modelMapper.map(categorydto, Category.class);
        Category savedcat = this.categoryRepo.save(cat);
        return this.modelMapper.map(savedcat, categoryDTO.class);
    }

    @Override
    public categoryDTO updateCategory(categoryDTO categorydto, Integer categoryId) {
        Category cat = this.modelMapper.map(categorydto, Category.class);
        Category savedcat = this.categoryRepo.save(cat);
        return this.modelMapper.map(savedcat,categoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));
        this.categoryRepo.delete(cat);
    }

    @Override
    public categoryDTO getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));

        return this.modelMapper.map(cat,categoryDTO.class);
    }

    @Override
    public List<categoryDTO> getCategories() {
        List<Category> ls = this.categoryRepo.findAll();
        return ls.stream().map((cat)-> this.modelMapper.map(cat, categoryDTO.class)).toList();
    }
}

