package com.rent.rentservice.mail.exception;

public class InvaildAuthCheckException extends RuntimeException {

    private static final String MESSAGE = "인증번호가 일치하지 않습니다";

    public InvaildAuthCheckException() {super(MESSAGE);}

    public InvaildAuthCheckException(Throwable cause) {super(MESSAGE, cause);}
}
