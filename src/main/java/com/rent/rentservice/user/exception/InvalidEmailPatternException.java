package com.rent.rentservice.user.exception;

public class InvalidEmailPatternException extends RuntimeException{
    private static final String MESSAGE = "이메일 형식이 아닙니다";

    public InvalidEmailPatternException() {super(MESSAGE);}

    public InvalidEmailPatternException(Throwable cause) {super(MESSAGE, cause);}
}
