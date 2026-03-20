package com.sanket.blog_app_apis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class categoryDTO {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4, message = "Title must be 4 or more characters")
    private String categoryTitle;
    @NotBlank
    private String categoryDescription;
}
