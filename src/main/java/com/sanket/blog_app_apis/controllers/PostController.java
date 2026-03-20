package com.sanket.blog_app_apis.controllers;

import com.sanket.blog_app_apis.config.AppConstants;
import com.sanket.blog_app_apis.entities.Post;
import com.sanket.blog_app_apis.payloads.ApiResponses;
import com.sanket.blog_app_apis.payloads.PostResponse;
import com.sanket.blog_app_apis.payloads.postDTO;
import com.sanket.blog_app_apis.services.FileService;
import com.sanket.blog_app_apis.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<postDTO> createPost(@RequestBody postDTO postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

        postDTO createdPost = this.postService.createPost(postDto,userId,categoryId);

        return new ResponseEntity<postDTO>(createdPost, HttpStatus.CREATED);

    }

    @GetMapping("{postId}")
    public ResponseEntity<postDTO> getPostById(@PathVariable Integer postId){
        postDTO postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<postDTO>> getPostsByUser(@PathVariable Integer userId){
        List<postDTO> postdtos = this.postService.getALlPostsByUser(userId);
        return new ResponseEntity<List<postDTO>>(postdtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<postDTO>> getPostsByCategory(@PathVariable Integer categoryId){
        List<postDTO> postdtos = this.postService.getALlPostsByCategory(categoryId);
        return new ResponseEntity<>(postdtos, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY ,required = false) String sortBy){
       PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PutMapping("/{postId}")
    public  ApiResponses updatePost(@RequestBody postDTO postDto ,@PathVariable Integer postId){
        postDTO updatedPost = this.postService.updatePost(postDto,postId);
        return new ApiResponses("Post is deleted successfully",true);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<postDTO>> searchPostsByTitle(@PathVariable("keywords") String keywords){
        List<postDTO> result = this.postService.searchPosts(keywords);
        return  new ResponseEntity<List<postDTO>>(result, HttpStatus.OK);
    }

    //post image upload

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<postDTO> uploadImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId ) throws IOException {


        postDTO postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path, image);

        postDto.setImageName(fileName);

        postDTO updatedPost = this.postService.updatePost(postDto,postId);

        return new ResponseEntity<postDTO>(updatedPost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}")
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}

