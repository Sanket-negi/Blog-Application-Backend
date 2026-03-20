package com.sanket.blog_app_apis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;

    private String content;

    @ManyToOne
    private Post post;
}
