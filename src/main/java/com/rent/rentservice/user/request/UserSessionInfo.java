package com.rent.rentservice.user.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 유저 세션 생성을 위한 객체
 * @author 김승진
 * @since 2023.01.13
 */

@Data
public class UserSessionInfo {

    @NotBlank
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String nickName;

    @Builder
    public UserSessionInfo(Long id, String email, String nickName) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
    }
}
