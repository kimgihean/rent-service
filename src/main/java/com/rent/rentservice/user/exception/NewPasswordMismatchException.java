package com.rent.rentservice.user.exception;

public class NewPasswordMismatchException extends RuntimeException{

    private static final String MESSAGE = "변경될 비밀번호가 일치하지 않습니다";

    public NewPasswordMismatchException() {super(MESSAGE);}

    public NewPasswordMismatchException(Throwable cause) {super(MESSAGE, cause);}
}
