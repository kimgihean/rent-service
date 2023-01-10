package com.rent.rentservice.user.exception;

public class InvalidEmailPattern extends RuntimeException{
    private static final String MESSAGE = "이메일 형식이 아닙니다";

    public InvalidEmailPattern() {super(MESSAGE);}

    public InvalidEmailPattern(Throwable cause) {super(MESSAGE, cause);}
}
