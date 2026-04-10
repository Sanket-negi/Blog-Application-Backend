package com.sanket.blog_app_apis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class postDTO {

    private Integer postId;

    private String postTitle;
    private String postContent;
    private String imageUrl;
    private Date addeddate;
    
    

    private categoryDTO category;
    private userDTO user;

    private Set<commentDTO> comments = new HashSet<>();

    //private String imageName= "default.png";
}
