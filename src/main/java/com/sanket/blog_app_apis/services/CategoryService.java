package com.sanket.blog_app_apis.services;

import com.sanket.blog_app_apis.payloads.categoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {

    //create
    categoryDTO createCategory(categoryDTO categorydto);

    //update
    categoryDTO updateCategory(categoryDTO categorydto, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //get
    categoryDTO getCategory(Integer categoryId);

    //getAll
    List<categoryDTO> getCategories();


    interface CouldService {
    }
}
