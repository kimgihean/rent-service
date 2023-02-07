package com.rent.rentservice.post.exception;

public class UpdatePostSessionException extends RuntimeException {
    private static final String MESSAGE = "게시글 수정 권한이 없습니다";

    public UpdatePostSessionException() {super(MESSAGE);}

    public UpdatePostSessionException(Throwable cause) {super(MESSAGE, cause);}
}
