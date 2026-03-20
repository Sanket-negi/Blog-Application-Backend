package com.sanket.blog_app_apis.services;

import com.sanket.blog_app_apis.payloads.commentDTO;
import org.springframework.stereotype.Service;

@Service
public interface commentService {

    commentDTO createComment(commentDTO commentDto, Integer postId);
    void deleteComment(Integer commentId);

}
