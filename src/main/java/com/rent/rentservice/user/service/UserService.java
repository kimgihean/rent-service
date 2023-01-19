package com.rent.rentservice.user.service;

import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.InvalidEmailPatternException;
import com.rent.rentservice.user.exception.OverlapUserEmailException;
import com.rent.rentservice.user.exception.UserNotFoundException;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.request.UserSessionInfo;
import com.rent.rentservice.util.common.CommonUtil;
import com.rent.rentservice.util.encryption.AES256;
import com.rent.rentservice.util.session.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * @description User 도메인 Service 레이어
 * @author 김승진
 * @since 2023.01.07
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final AES256 aes256;

    // 회원가입
    @Transactional
    public void join(JoinForm request) throws Exception {

        // 이메일 형식 검사
        if(!CommonUtil.isValidEmail(request.getEmail())) {
            throw new InvalidEmailPatternException();
        }

        // 이메일 중복 유효성 검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new OverlapUserEmailException();
        }

        // Request로 부터 User 객체 만들어서 -> 저장
        User user = User.builder()
                        .name(request.getName())
                        .nickName(request.getNickName())
                        .email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .password(aes256.encrypt(request.getPassword()))
                        .address(request.getAddress())
                        .build();
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginForm request, HttpSession session) throws Exception {

        // 이메일 형식 검사
        if(!CommonUtil.isValidEmail(request.getEmail())) {
            throw new InvalidEmailPatternException();
        }

        // 유저 정보 조회 => 없으면 Error Throw
        User user = userRepository.findByEmailAndPassword(request.getEmail(), aes256.encrypt(request.getPassword()))
                .orElseThrow(UserNotFoundException::new);

        // 조회된 유저 객체로부터 세션 객체 생성
        UserSessionInfo userSessionInfo = UserSessionInfo.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();

        // 세션 정보를 바탕으로 세션 생성
        SessionUtil.setLoginInfo(session, userSessionInfo);
    }

    // 로그아웃
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
