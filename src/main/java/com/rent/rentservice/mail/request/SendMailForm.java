package com.rent.rentservice.mail.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.nio.channels.Pipe;

/**
 * @description Mail 전송 API Request 객체
 * @author 김승진
 * @since 2023.01.20
 */

@Data
public class SendMailForm {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    public SendMailForm() {

    }

    @Builder
    public SendMailForm(String email) {
        this.email = email;
    }
}
