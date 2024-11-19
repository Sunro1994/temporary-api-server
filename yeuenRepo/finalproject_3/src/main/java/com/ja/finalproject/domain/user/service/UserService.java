package com.ja.finalproject.domain.user.service;

import java.util.List;
import java.util.UUID;

import com.ja.finalproject.domain.user.repository.UserRepository;
import com.ja.finalproject.global.mailsender.MailSendService;
import com.ja.finalproject.global.response.dto.RestResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ja.finalproject.domain.user.dto.HobbyCategoryDto;
import com.ja.finalproject.domain.user.dto.MailAuthDto;
import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.dto.UserHobbyDto;
import com.ja.finalproject.domain.user.mapper.UserSqlMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSqlMapper userSqlMapper;
    private final MailSendService mailSendService;
    private final UserRepository userRepository;

    public void register(UserDto userDto, List<Integer> hobbyIdList) {
        createUser(userDto, hobbyIdList);
        String authKey = createMailAuth(userDto.getId());
        mailSendService.sendMail(userDto.getEmail(), authKey);
    }

    private void createUser(UserDto userDto, List<Integer> hobbyIdList) {
        userSqlMapper.createUser(userDto);
        int lastUserPk = userDto.getId();

        for (int hobbyId : hobbyIdList) {
            UserHobbyDto userHobbyDto = new UserHobbyDto();
            userHobbyDto.setUser_id(lastUserPk);
            userHobbyDto.setHobby_id(hobbyId);
            userSqlMapper.createUserHobby(userHobbyDto);
        }
    }

    private String createMailAuth(int userId) {
        String authKey = UUID.randomUUID().toString();
        MailAuthDto mailAuthDto = new MailAuthDto();
        mailAuthDto.setUser_id(userId);
        mailAuthDto.setAuth_key(authKey);
        userSqlMapper.createMailAuth(mailAuthDto);
        return authKey;
    }


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

    public RestResponseDto existsId(String userId) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        responseDto.add("isExist", userRepository.existsUserByUserId(userId));
        return responseDto;
    }
}
