package com.rent.rentservice.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.service.PostService;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.encryption.AES256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description PostController 테스트코드
 * @author 김기현
 * @since 2023.01.16
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc // MockMvc 빈으로 등록
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AES256 aes256;

    MockHttpSession session;

    @BeforeEach
    void clean() throws Exception {
        // 임시 회원 정보 저장하고 로그인 시키기
        session = new MockHttpSession();
        JoinForm joinRequest = JoinForm.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();
        userService.join(joinRequest);

        LoginForm loginRequest = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();
        userService.login(loginRequest, session);

        postRepository.deleteAll();
    }

    @Test @DisplayName("게시글 작성시 DB 저장") //todo @DisplayName 한글 작성시 폰트 깨짐 수정
    void CreateTest() throws Exception {

        // given
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(0)
                .text("내용 테스트")
                .build();

        // objectMapper : Json 타입으로 convert
        String postJson = objectMapper.writeValueAsString(postRequest);

        // when
        mockMvc.perform(post("/posts/create")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)  // 객체를 변환해줄 타입
                        .content(postJson))             // 위 타입으로 저장할 객체
                .andExpect(status().isOk())             // status 응답
                .andDo(print());                        // 응답값 프린트

        // then
        Post post = postRepository.findAll().get(0);

        assertEquals(1L, post.getPostID());         // 두 개체의 값이 같은지 확인
        assertEquals("제목 테스트", post.getTitle());
        assertEquals(0, post.getFavorite());
        assertEquals("내용 테스트", post.getText());

    }

    @Test @DisplayName("게시글 작성시 세션 아웃 검사")
    void CreateSessionTest() {

    }

}
