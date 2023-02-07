package com.rent.rentservice.post.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description Post Update Form
 * @author ±è±âÇö
 * @since 2023.02.07
 */
@Data
public class PostUpdateForm {

    private String title;

    private String text;

    @Builder
    public PostUpdateForm(String title, String text) {
        this.text = text;
        this.title = title;
    }
}
