package com.rent.rentservice.user.exception;

/**
 * @description 유저 정보 조회 실패 -> Error Throw
 * @author 김승진
 * @since 2023.01.13
 */

public class UserNotFound extends RuntimeException {

    private static final String MESSAGE = "이메일 또는 비밀번호를 올바르게 입력해주세요";

    public UserNotFound() {super(MESSAGE);}

    public UserNotFound(Throwable cause) {super(MESSAGE, cause);}
}
