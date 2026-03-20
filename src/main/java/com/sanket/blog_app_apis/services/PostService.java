package com.sanket.blog_app_apis.services;

import com.sanket.blog_app_apis.entities.Category;
import com.sanket.blog_app_apis.entities.Post;
import com.sanket.blog_app_apis.payloads.PostResponse;
import com.sanket.blog_app_apis.payloads.postDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    //create
    postDTO createPost(postDTO postDto, Integer userId, Integer categoryId);

    //update
    postDTO updatePost(postDTO postDTO, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get post
    postDTO getPostById(Integer postId);

    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

    //get all posts by category
    List<postDTO> getALlPostsByCategory(Integer categoryId);

    //get all posts by User
    List<postDTO> getALlPostsByUser(Integer userId);

    //search posts by keyword
    List<postDTO> searchPosts(String keyword);
}
