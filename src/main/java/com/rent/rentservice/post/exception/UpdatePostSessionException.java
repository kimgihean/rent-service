package com.rent.rentservice.post.exception;

public class UpdatePostSessionException extends RuntimeException {
    private static final String MESSAGE = "�Խñ� ���� ������ �����ϴ�";

    public UpdatePostSessionException() {super(MESSAGE);}

    public UpdatePostSessionException(Throwable cause) {super(MESSAGE, cause);}
}
