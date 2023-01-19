package com.rent.rentservice.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 비밀번호 변경 API Request 객체
 * @author 김승진
 * @since 2023.01.07
 */

@Data
public class ChangePwForm {

    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    private String curPassword;

    @NotBlank(message = "변경하실 비밀번호를 입력해주세요")
    private String newPassword;

    @NotBlank(message = "비밀번호 재입력란을 확인해주세요")
    private String newPasswordCheck;
}
