package com.rent.rentservice.mail.service;

import com.rent.rentservice.mail.domain.AuthNum;
import com.rent.rentservice.mail.exception.InvaildAuthCheckException;
import com.rent.rentservice.mail.repository.MailRepository;
import com.rent.rentservice.mail.request.AuthCheckForm;
import com.rent.rentservice.mail.request.SendMailForm;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.InvalidEmailPatternException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    MailService mailService;

    @Autowired
    MailRepository mailRepository;

    @BeforeEach
    void clean() {
        mailRepository.deleteAll();
    }

    @Test
    @DisplayName("인증 메일 발송")
    void test1() throws Exception {

        //given
        SendMailForm request = SendMailForm.builder()
                .email("dahyun101107@naver.com")
                .build();

        //when
        mailService.sendMessage(request);

        //then
        assertEquals(1L, mailRepository.count());
        AuthNum authNum = mailRepository.findAll().get(0);
        assertEquals("dahyun101107@naver.com", authNum.getEmail());
        assertEquals(8, authNum.getAuthNum().length());
    }

    @Test
    @DisplayName("인증 메일 발송 - 이메일 형식 아님")
    void test2() throws Exception {

        //given
        SendMailForm request = SendMailForm.builder()
                .email("test")
                .build();

        //expected
        assertThrows(InvalidEmailPatternException.class, () -> {
            mailService.sendMessage(request);
        });
    }

    @Test
    @DisplayName("인증번호 유효성 체크 - 이메일 형식 아님")
    void test3() throws Exception {

        //given
        AuthCheckForm request = AuthCheckForm.builder()
                .email("test")
                .authNum("12345678")
                .build();

        //expected
        assertThrows(InvalidEmailPatternException.class, () -> {
            mailService.authNumCheck(request);
        });
    }
    @Test
    @DisplayName("인증번호 유효성 체크 - 인증번호 불일치")
    void test4() throws Exception {

        //given
        AuthNum authNum = AuthNum.builder()
                .email("test@test.com")
                .authNum("12345678")
                .build();

        mailRepository.save(authNum);

        AuthCheckForm request = AuthCheckForm.builder()
                .email("test@test.com")
                .authNum("24681357")
                .build();

        //expected
        assertThrows(InvaildAuthCheckException.class, () -> {
            mailService.authNumCheck(request);
        });
    }

}