package com.rent.rentservice.post.exception;

public class NonePostException extends Exception {
    private static final String MESSAGE = "�Խñ��� ã�� �� �����ϴ�.";
    public NonePostException() {
        super(MESSAGE);
    }
}
