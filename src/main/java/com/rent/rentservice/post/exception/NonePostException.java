package com.rent.rentservice.post.exception;

public class NonePostException extends Exception {
    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";
    public NonePostException() {
        super(MESSAGE);
    }
}
