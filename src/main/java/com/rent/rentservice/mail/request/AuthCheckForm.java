package com.rent.rentservice.mail.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 메일 인증번호 체크 Request 객체
 * @author 김승진
 * @since 2023.01.21
 */

@Data
public class AuthCheckForm {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "인증번호를 입력해주세요")
    private String authNum;

    @Builder
    public AuthCheckForm(String email, String authNum) {
        this.email = email;
        this.authNum = authNum;
    }
}
