package com.sanket.blog_app_apis.services.impl;

import com.sanket.blog_app_apis.entities.Category;
import com.sanket.blog_app_apis.entities.Post;
import com.sanket.blog_app_apis.entities.User;
import com.sanket.blog_app_apis.exceptions.ResourceNotFoundException;
import com.sanket.blog_app_apis.payloads.PostResponse;
import com.sanket.blog_app_apis.payloads.postDTO;
import com.sanket.blog_app_apis.repositories.CategoryRepo;
import com.sanket.blog_app_apis.repositories.PostRepo;
import com.sanket.blog_app_apis.repositories.UserRepo;
import com.sanket.blog_app_apis.services.PostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class postServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public postDTO createPost(postDTO postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddeddate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);
        return this.modelMapper.map(savedPost, postDTO.class);
    }

    @Override
    public postDTO updatePost(postDTO postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setImageName(postDto.getImageName());

        return this.modelMapper.map(post, postDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepo.deleteById(postId);
    }

    @Transactional
    @Override
    public postDTO getPostById(Integer postId) {
        Post post =  postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(post, postDTO.class);

    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> postPage = this.postRepo.findAll(p);
        List<Post> posts = postPage.getContent();

        List<postDTO> postDTOS = posts.stream().map((post)->this.modelMapper.map(post, postDTO.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public List<postDTO> getALlPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        return posts.stream().map((post -> this.modelMapper.map(post, postDTO.class))).toList();
    }

    @Override
    public List<postDTO> getALlPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Post> posts = this.postRepo.findByUser(user);

        List<postDTO> postdtos = posts.stream()
                .map(post -> this.modelMapper.map(post, postDTO.class))
                .toList();

        return postdtos;
    }


    @Override
    public List<postDTO> searchPosts(String keyword) {
        List<Post> posts =this.postRepo.findByPostTitleContaining(keyword);
        List<postDTO> postDTOS = posts.stream().map(post-> this.modelMapper.map(post, postDTO.class)).toList();
        return  postDTOS;
    }
}
