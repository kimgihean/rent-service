package com.rent.rentservice.exception.controller;

import com.rent.rentservice.exception.response.ErrorResponse;
import com.rent.rentservice.user.exception.InvalidEmailPatternException;
import com.rent.rentservice.user.exception.OverlapUserEmailException;
import com.rent.rentservice.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description Custom 예외처리
 * @author 김승진
 * @since 2023.01.07
 */

@ControllerAdvice
public class ExceptionController {

    // API 요청에 필요한 파라미터 공백시 JSON 형태로 Error 내용 리턴
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invaildRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("입력되지 않은 값이 존재합니다")
                .build();

        for(FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addVaildation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResponse;
    }

    // 회원가입 이메일 중복 -> Throw
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OverlapUserEmailException.class)
    @ResponseBody
    public ErrorResponse invaildRequestHandler(OverlapUserEmailException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }

    // 회원가입 이메일 형식아님 -> Throw
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailPatternException.class)
    @ResponseBody
    public ErrorResponse invaildRequestHandler(InvalidEmailPatternException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }

    // 이메일 + 비밀번호로 조회했는데 해당 유저 없음 -> Throw
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ErrorResponse invaildRequestHandler(UserNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }
}
