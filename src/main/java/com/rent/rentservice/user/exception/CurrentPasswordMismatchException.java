package com.rent.rentservice.user.exception;

public class CurrentPasswordMismatchException extends RuntimeException{

    private static final String MESSAGE = "현재 비밀번호가 일치하지 않습니다";

    public CurrentPasswordMismatchException() {super(MESSAGE);}

    public CurrentPasswordMismatchException(Throwable cause) {super(MESSAGE, cause);}
}
