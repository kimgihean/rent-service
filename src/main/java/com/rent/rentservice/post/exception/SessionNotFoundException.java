package com.rent.rentservice.post.exception;

public class SessionNotFoundException extends RuntimeException {
    private static final String MESSAGE = "세션이 종료되었습니다.";

    public SessionNotFoundException() {
        super(MESSAGE);
    }
}
