package com.rent.rentservice.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.util.encryption.AES256;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @description UserController 테스트코드
 * @author 김승진
 * @since 2023.01.13
 */

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AES256 aes256;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("회원가입 요청시 DB에 값 저장")
    void test1() throws Exception {

        //given
        JoinForm request = JoinForm.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/v1/join")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, userRepository.count());

        User user = userRepository.findAll().get(0);
        assertEquals(1L, user.getUserId());
        assertEquals("홍길동", user.getName());
        assertEquals("닉네임", user.getNickName());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("1234", aes256.decrypt(user.getPassword()));
        assertEquals("경기도", user.getAddress());

    }

    @Test
    @DisplayName("회원가입 파라미터 Vaild 테스트")
    void test2() throws Exception {

        //given
        JoinForm request = JoinForm.builder()
                .name("홍길동")
                .nickName("")
                .email("test@test.com")
                .phoneNumber("")
                .password("1234")
                .address("경기도")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/api/v1/join")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("입력되지 않은 값이 존재합니다"))
                .andExpect(jsonPath("$.vaildation.nickName").value("닉네임을 입력해주세요"))
                .andExpect(jsonPath("$.vaildation.phoneNumber").value("전화번호를 입력해주세요"))
                .andDo(print());
    }
}