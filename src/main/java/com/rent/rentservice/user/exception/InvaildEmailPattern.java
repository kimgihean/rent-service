package com.rent.rentservice.user.exception;

public class InvaildEmailPattern extends RuntimeException{
    private static final String MESSAGE = "이메일 형식이 아닙니다";

    public InvaildEmailPattern() {super(MESSAGE);}

    public InvaildEmailPattern(Throwable cause) {super(MESSAGE, cause);}
}
