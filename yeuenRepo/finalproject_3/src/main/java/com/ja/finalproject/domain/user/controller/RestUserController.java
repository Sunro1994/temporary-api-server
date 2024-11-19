package com.ja.finalproject.domain.user.controller;

import com.ja.finalproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ja.finalproject.global.response.dto.RestResponseDto;
import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController // @Controller + @ResponseBody
@RequestMapping("api/user")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService useService;
    private final UserRepository userRepository;
    private final UserService userService;


    @RequestMapping("getSessionId")
    public RestResponseDto getSessionId(HttpSession session) {
        RestResponseDto responseDto = userService.getSessionId(session);
        return responseDto;
    }


    @RequestMapping("existsId")
    public RestResponseDto existsId(@RequestParam("userId") String userId) {
        RestResponseDto responseDto = userService.existsId(userId);
        return responseDto;
    }

}
