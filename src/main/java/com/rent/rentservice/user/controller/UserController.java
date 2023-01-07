package com.rent.rentservice.user.controller;

import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description User 도메인 Controller
 * @description User 관련 API
 * @author 김승진
 * @since 2023.01.07
 */

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/api/v1/join")
    public void join(@RequestBody @Valid JoinForm request) throws Exception {
        userService.join(request);
    }
}
