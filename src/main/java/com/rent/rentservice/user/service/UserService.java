package com.rent.rentservice.user.service;

import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.InvalidEmailPattern;
import com.rent.rentservice.user.exception.OverlapUserEmail;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.util.common.CommonUtil;
import com.rent.rentservice.util.encryption.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void join(JoinForm request) throws Exception {

        if(!CommonUtil.isValidEmail(request.getEmail())) {
            throw new InvalidEmailPattern();
        }

        // 이메일 중복 유효성 검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new OverlapUserEmail();
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
}
