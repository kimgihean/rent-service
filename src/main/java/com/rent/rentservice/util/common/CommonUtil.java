package com.rent.rentservice.util.common;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 유효성 검사와 관련된 유틸 함수를 담는 클래스
 * @author 김승진
 * @since 2023.01.07
 */

@Component
public class CommonUtil {

    public static boolean isValidEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
