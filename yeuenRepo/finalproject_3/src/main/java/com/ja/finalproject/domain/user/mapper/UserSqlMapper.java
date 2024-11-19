package com.ja.finalproject.domain.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ja.finalproject.domain.user.dto.HobbyCategoryDto;
import com.ja.finalproject.domain.user.dto.MailAuthDto;
import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.dto.UserHobbyDto;

@Mapper
public interface UserSqlMapper {
    public void createUser(UserDto userDto);
    // insert, update, delete = void
    // select = 적절한 리턴 타입 필요
    public UserDto findByUserIdAndPassword(UserDto userDto);
    public UserDto findById(int id);

    // 취미 관련
    public List<HobbyCategoryDto> findHobbyCategoryAll();
    public void createUserHobby(UserHobbyDto userHobbyDto);

    // 메일 인증
    public void createMailAuth(MailAuthDto mailAuthDto);
    public void updateMailAuthComplete(String auth_key);


    // 회원 존재 유무
    public int countUserByUserId(String user_id);
}
