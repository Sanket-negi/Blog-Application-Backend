package com.sanket.blog_app_apis.controllers;

import com.sanket.blog_app_apis.payloads.ApiResponses;
import com.sanket.blog_app_apis.payloads.commentDTO;
import com.sanket.blog_app_apis.services.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private commentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<commentDTO> createComment(@RequestBody commentDTO commentDto , @PathVariable Integer postId){

        commentDTO createComment = this.commentService.createComment(commentDto,postId);
        return  new ResponseEntity<commentDTO>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponses> createComment( @PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);
        return  new ResponseEntity<ApiResponses>(new ApiResponses("Deleted Successfully", true), HttpStatus.CREATED);
    }



}
