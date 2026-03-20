package com.sanket.blog_app_apis.payloads;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class userDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 4 , message = "Username must be min 4 characters")
    private String username;

    @Email(message = "Email address not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3 , message = "Password must be min 3 characters")
    @Size(max = 10 , message = "Password must be max 10 characters")
    private String password;

    @NotEmpty
    private String about;
}
