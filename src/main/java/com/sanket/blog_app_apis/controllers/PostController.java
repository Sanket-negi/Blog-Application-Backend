package com.sanket.blog_app_apis.controllers;

import com.sanket.blog_app_apis.config.AppConstants;
import com.sanket.blog_app_apis.payloads.ApiResponses;
import com.sanket.blog_app_apis.payloads.PostResponse;
import com.sanket.blog_app_apis.payloads.postDTO;
import com.sanket.blog_app_apis.services.CloudService;
import com.sanket.blog_app_apis.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CloudService cloudinaryService;

    // ✅ CREATE POST
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<postDTO> createPost(
            @RequestBody postDTO postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        postDTO createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // ✅ GET SINGLE POST
    @GetMapping("/{postId}")
    public ResponseEntity<postDTO> getPostById(@PathVariable Integer postId) {
        postDTO postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    // ✅ GET POSTS BY USER
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<postDTO>> getPostsByUser(@PathVariable Integer userId) {
        List<postDTO> postdtos = this.postService.getALlPostsByUser(userId);
        return new ResponseEntity<>(postdtos, HttpStatus.OK);
    }

    // ✅ GET POSTS BY CATEGORY
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<postDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<postDTO> postdtos = this.postService.getALlPostsByCategory(categoryId);
        return new ResponseEntity<>(postdtos, HttpStatus.OK);
    }

    // ✅ GET ALL POSTS (PAGINATION)
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {

        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // ✅ DELETE POST
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponses> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponses("Post deleted successfully", true), HttpStatus.OK);
    }

    // ✅ UPDATE POST
    @PutMapping("/{postId}")
    public ResponseEntity<postDTO> updatePost(
            @RequestBody postDTO postDto,
            @PathVariable Integer postId) {

        postDTO updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // ✅ SEARCH POSTS
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<postDTO>> searchPostsByTitle(@PathVariable String keywords) {
        List<postDTO> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ✅ UPLOAD IMAGE (CLOUDINARY)
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<postDTO> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        postDTO postDto = this.postService.getPostById(postId);

        // Upload to Cloudinary
        String imageUrl = cloudinaryService.uploadFile(image);

        // Save URL in DB
        postDto.setImageUrl(imageUrl);

        postDTO updatedPost = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
}

