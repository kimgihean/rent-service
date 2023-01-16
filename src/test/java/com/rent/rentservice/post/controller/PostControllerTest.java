package com.rent.rentservice.post.controller;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.service.PostService;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.request.UserSessionInfo;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.encryption.AES256;
import com.rent.rentservice.util.session.SessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description PostController 테스트코드
 * @author 김기현
 * @since 2023.01.16
 */

@SpringBootTest
@Transactional
public class PostControllerTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AES256 aes256;

    @Test @DisplayName("게시글 작성 테스트")
    void CreateTest() throws Exception {
        // 임시 세션 생성
        MockHttpSession session = new MockHttpSession();
        SessionUtil sessionUtil = null;

        //given
        // 임시 유저 객체 생성
        User user = User.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password(aes256.encrypt("1234"))
                .address("경기도")
                .build();

        // 저장
        userRepository.save(user);

        // 로그인
        LoginForm userLoginRequest = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        // 로그인하여 임시 세션에 저장
        userService.login(userLoginRequest, session);

        // 확인할 임시 Post 저장
        PostCreateForm postCreateRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(15)
                .text("내용 테스트")
                .build();

        // 세션에 저장될 정보(id, email, nickname) 저장
        UserSessionInfo sessionInfo = UserSessionInfo.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();

        // session util 에 저장
        sessionUtil.setLoginInfo(session, sessionInfo);

        //when
        // 임시 post, 임시 session, 임시 session 에 저장된 정보를 create
        postService.create(postCreateRequest, session, sessionUtil);

        //then
        Post post = postRepository.findAll().get(0);
        assertEquals("제목 테스트", post.getTitle());
        assertEquals(15, post.getFavorite());
        assertEquals("내용 테스트", post.getText());
    }
}
