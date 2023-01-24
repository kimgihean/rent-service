package com.rent.rentservice.mail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.rentservice.mail.domain.AuthNum;
import com.rent.rentservice.mail.repository.MailRepository;
import com.rent.rentservice.mail.request.AuthCheckForm;
import com.rent.rentservice.mail.request.SendMailForm;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.request.JoinForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description MailController 테스트코드
 * @author 김승진
 * @since 2023.01.24
 */

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MailRepository mailRepository;

    @BeforeEach
    void clean() {
        mailRepository.deleteAll();
    }

    @Test
    @DisplayName("메일 발송 - 인증번호 요청시 인증 데이터 DB에 저장")
    void test1() throws Exception {

        //given
        SendMailForm request = SendMailForm.builder()
                .email("nestle7474@gachon.ac.kr")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/sendAuthMail")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, mailRepository.count());

        AuthNum authNum = mailRepository.findAll().get(0);
        assertEquals("nestle7474@gachon.ac.kr", authNum.getEmail());
    }

    @Test
    @DisplayName("메일 발송 - 인증번호 요청시 이메일 값은 필수")
    void test2() throws Exception {

        //given
        SendMailForm request = SendMailForm.builder()
                .email("")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/sendAuthMail")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("입력되지 않은 값이 존재합니다"))
                .andExpect(jsonPath("$.vaildation.email").value("이메일을 입력해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("메일 발송 - 이메일 형식 아님")
    void test6() throws Exception {

        //given
        SendMailForm request = SendMailForm.builder()
                .email("test123")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/sendAuthMail")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("420"))
                .andExpect(jsonPath("$.message").value("이메일 형식이 아닙니다"))
                .andDo(print());
    }

    @Test
    @DisplayName("인증번호 체크 - 성공")
    void test3() throws Exception {

        //given
        AuthNum authNum = AuthNum.builder()
                .email("test@test.com")
                .authNum("123456")
                .build();

        mailRepository.save(authNum);

        AuthCheckForm request = AuthCheckForm.builder()
                .email("test@test.com")
                .authNum("123456")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/mailAuthCheck")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("인증번호 체크 - 이메일 값은 필수")
    void test4() throws Exception {

        //given
        AuthNum authNum = AuthNum.builder()
                .email("test@test.com")
                .authNum("123456")
                .build();

        mailRepository.save(authNum);

        AuthCheckForm request = AuthCheckForm.builder()
                .email("")
                .authNum("123456")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/mailAuthCheck")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("입력되지 않은 값이 존재합니다"))
                .andExpect(jsonPath("$.vaildation.email").value("이메일을 입력해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("인증번호 체크 - 이메일 형식 아님")
    void test5() throws Exception {

        //given
        AuthNum authNum = AuthNum.builder()
                .email("test@test.com")
                .authNum("123456")
                .build();

        mailRepository.save(authNum);

        AuthCheckForm request = AuthCheckForm.builder()
                .email("test123")
                .authNum("123456")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/mailAuthCheck")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("420"))
                .andExpect(jsonPath("$.message").value("이메일 형식이 아닙니다"))
                .andDo(print());
    }

    @Test
    @DisplayName("인증번호 체크 - 이메일 형식 아님")
    void test7() throws Exception {

        //given
        AuthNum authNum = AuthNum.builder()
                .email("test@test.com")
                .authNum("123456")
                .build();

        mailRepository.save(authNum);

        AuthCheckForm request = AuthCheckForm.builder()
                .email("test@test.com")
                .authNum("654321")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/mailAuthCheck")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("460"))
                .andExpect(jsonPath("$.message").value("인증번호가 일치하지 않습니다"))
                .andDo(print());
    }
}