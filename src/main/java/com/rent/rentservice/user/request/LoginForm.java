package com.rent.rentservice.user.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 로그인 API Request 객체
 * @author 김승진
 * @since 2023.01.13
 */

@Data
public class LoginForm {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Builder
    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
