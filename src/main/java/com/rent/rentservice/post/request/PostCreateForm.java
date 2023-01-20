package com.rent.rentservice.post.request;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.user.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

//todo category
@Data
public class PostCreateForm {
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요")
    private String text;

    private int favorite;

    @Builder
    public PostCreateForm(String title, String text, int favorite) {
        this.title = title;
        this.text = text;
        this.favorite = favorite;
    }
}
