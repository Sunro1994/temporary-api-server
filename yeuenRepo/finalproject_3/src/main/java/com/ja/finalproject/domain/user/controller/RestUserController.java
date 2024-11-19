package com.ja.finalproject.domain.user.controller;

import com.ja.finalproject.domain.user.repository.UserRepository;
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
public class RestUserController {

    @Autowired
    private UserService useService;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping("getSessionId")
    public RestResponseDto getSessionId(HttpSession session) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");

        if(userDto == null){
            responseDto.setResult("fail");
            responseDto.setReason("인증 되지 않았습니다.");
        } else {
            responseDto.add("id", userDto.getId());
        }
        
        return responseDto;
    }


    @RequestMapping("existsId")
    public RestResponseDto existsId(@RequestParam("userId") String userId) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        responseDto.add("isExist", userRepository.existsUserByUserId(userId));

        return responseDto;
    }

}
