package com.rent.rentservice.post.exception;

public class SessionIdNotFoundException extends RuntimeException {

    private static final String MESSAGE = "세션 ID가 유저정보와 일치하지 않습니다";
    public SessionIdNotFoundException() {
        super(MESSAGE);
    }
}
