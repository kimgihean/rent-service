package com.rent.rentservice.user.service;

import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.InvalidEmailPatternException;
import com.rent.rentservice.user.exception.OverlapUserEmailException;
import com.rent.rentservice.user.exception.UserNotFoundException;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.util.encryption.AES256;
import com.rent.rentservice.util.session.SessionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AES256 aes256;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
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

        //when
        userService.join(request);

        //then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("홍길동", user.getName());
        assertEquals("닉네임", user.getNickName());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("01012345678", user.getPhoneNumber());
        assertEquals("1234", aes256.decrypt(user.getPassword()));
        assertEquals("경기도", user.getAddress());
    }

    @Test
    @DisplayName("회원가입 - 이메일 중복")
    void test2() throws Exception {

        //given
        JoinForm firstMember = JoinForm.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();

        userService.join(firstMember);

        JoinForm secondMember = JoinForm.builder()
                .name("김철수")
                .nickName("김철수")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();

        //expected
        assertThrows(OverlapUserEmailException.class, () -> {
            userService.join(secondMember);
        });
    }

    @Test
    @DisplayName("회원가입 - 이메일 형식 아님")
    void test3() throws Exception {

        //given
        JoinForm request = JoinForm.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();

        //expected
        assertThrows(InvalidEmailPatternException.class, () -> {
            userService.join(request);
        });
    }

    @Test
    @DisplayName("로그인")
    void test4() throws Exception {

        //given
        MockHttpSession session = new MockHttpSession();

        User user = User.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password(aes256.encrypt("1234"))
                .address("경기도")
                .build();

        userRepository.save(user);

        LoginForm request = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        //when
        userService.login(request, session);

        //then
        assertEquals("test@test.com", SessionUtil.getLoginMemberEmail(session));
        assertEquals("닉네임", SessionUtil.getLoginMemberNickname(session));
    }

    @Test
    @DisplayName("로그인 - 이메일 형식 아님")
    void test5() throws Exception {

        //given
        MockHttpSession session = new MockHttpSession();

        LoginForm request = LoginForm.builder()
                .email("test.com")
                .password("1234")
                .build();

        //expected
        assertThrows(InvalidEmailPatternException.class, () -> {
            userService.login(request, session);
        });
    }

    @Test
    @DisplayName("로그인 - 유저 정보 조회 실패")
    void test6() throws Exception {

        //given
        MockHttpSession session = new MockHttpSession();

        LoginForm request = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        //expected
        assertThrows(UserNotFoundException.class, () -> {
            userService.login(request, session);
        });
    }
}