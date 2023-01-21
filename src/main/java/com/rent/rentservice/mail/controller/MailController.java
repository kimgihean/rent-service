package com.rent.rentservice.mail.controller;

import com.rent.rentservice.mail.request.AuthCheckForm;
import com.rent.rentservice.mail.request.SendMailForm;
import com.rent.rentservice.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description Mail 전송 및 인증 API Controller
 * @author 김승진
 * @since 2023.01.20
 */

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/api/v1/sendAuthMail")
    public void sendAuthMail(@RequestBody @Valid SendMailForm request) throws Exception {
        mailService.sendMessage(request);
    }

    @PostMapping("/api/v1/mailAuthCheck")
    public void authCheck(@RequestBody @Valid AuthCheckForm request) {
        mailService.authNumCheck(request);
    }
}
