package com.sanket.blog_app_apis.services.impl;

import com.sanket.blog_app_apis.entities.Comment;
import com.sanket.blog_app_apis.entities.Post;
import com.sanket.blog_app_apis.exceptions.ResourceNotFoundException;
import com.sanket.blog_app_apis.payloads.commentDTO;
import com.sanket.blog_app_apis.repositories.CommentRepo;
import com.sanket.blog_app_apis.repositories.PostRepo;
import com.sanket.blog_app_apis.services.commentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class commentServiceImpl implements commentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public commentDTO createComment(commentDTO commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        Comment savedComment =  this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, commentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));
        this.commentRepo.delete(comment);
    }
}
