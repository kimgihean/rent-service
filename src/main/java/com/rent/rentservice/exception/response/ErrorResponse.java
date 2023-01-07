package com.rent.rentservice.exception.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * {
 *     "code" : "400"
 *     "message" : "잘못된 요청입니다."
 *     "vaildation" : {
 *          "title" : "값을 입력해주세요"
 *     }
 * }
 */

/**
 * @description 에러시 리턴해줄 공통 객체
 * @author 김승진
 * @since 2023.01.07
 */

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> vaildation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addVaildation(String fieldName, String errorMessage) {
        this.vaildation.put(fieldName, errorMessage);
    }
}
