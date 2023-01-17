package com.rent.rentservice.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.service.PostService;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.encryption.AES256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;
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

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test // @DisplayName("게시글 작성시 DB 저장") > todo @DisplayName 한글 작성시 폰트 깨짐 수정
    void CreateTest() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();

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
                        .session(mockHttpSession)
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

//
//        //given
//        // 세션에 들어갈 임시 유저 객체 생성
//        User user = User.builder()
//                .name("홍길동")
//                .nickName("닉네임")
//                .email("test@test.com")
//                .phoneNumber("01012345678")
//                .password(aes256.encrypt("1234"))
//                .address("경기도")
//                .build();
//
//        // 저장
//        userRepository.save(user);
//
//        // 로그인
//        LoginForm userLoginRequest = LoginForm.builder()
//                .email("test@test.com")
//                .password("1234")
//                .build();
//
//        // 로그인하여 임시 세션에 저장
//        userService.login(userLoginRequest, session);
//
//        // 세션에 저장될 정보(id, email, nickname) 저장
//        UserSessionInfo sessionInfo = UserSessionInfo.builder()
//                .id(user.getUserId())
//                .email(user.getEmail())
//                .nickName(user.getNickName())
//                .build();
//
//        // session util 에 저장
//        SessionUtil.setLoginInfo(session, sessionInfo);
//
//        // 확인할 임시 Post 저장
//        PostCreateForm postCreateRequest = PostCreateForm.builder()
//                .title("제목 테스트")
//                .favorite(0)
//                .text("내용 테스트")
//                .build();
//
//        //when
//        // 임시 post, 임시 session, 임시 session 에 저장된 정보를 create
//        postService.create(postCreateRequest, session);
//
//        //then
//        Post post = postRepository.findAll().get(0);
//        assertEquals("제목 테스트", post.getTitle());
//        assertEquals(0, post.getFavorite());
//        assertEquals("내용 테스트", post.getText());
    }

    @Test @DisplayName("게시글 작성시 세션 아웃 검사")
    void CreateSessionTest() {
        // 임시 세션 생성
        MockHttpSession session = new MockHttpSession();

        // given

        // when

        // then
    }

    @Test @DisplayName("게시글 작성시 회원 ID 확인 검사")
    void CreateSessionIdTest() {

    }

    // @Test @DisplayName("")
}
