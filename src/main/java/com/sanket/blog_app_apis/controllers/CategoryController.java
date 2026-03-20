package com.sanket.blog_app_apis.controllers;

import com.sanket.blog_app_apis.payloads.ApiResponses;
import com.sanket.blog_app_apis.payloads.categoryDTO;
import com.sanket.blog_app_apis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<categoryDTO> createCategory(@Valid @RequestBody categoryDTO categoryDto){
        categoryDTO createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<categoryDTO> updateUser(@Valid @RequestBody categoryDTO categoryDto, @PathVariable Integer categoryId){
        categoryDTO updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer cId){
        this.categoryService.deleteCategory(cId);
        return new ResponseEntity<>(new ApiResponses("Category is deleted successfully",true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<categoryDTO>> getCategories(){
        return ResponseEntity.ok(this.categoryService.getCategories());

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Integer cid){
        categoryDTO cat = this.categoryService.getCategory(cid);
        return ResponseEntity.ok(cat);
    }
}
