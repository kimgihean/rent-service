package com.rent.rentservice.user.request;

import com.rent.rentservice.user.domain.User;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 회원가입 API Request 객체
 * @author 김승진
 * @since 2023.01.07
 */

@Data
public class JoinForm {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickName;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Builder
    public JoinForm(String name, String nickName, String email, String phoneNumber, String password) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
