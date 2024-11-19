package com.ja.finalproject.domain.user.repository;

import com.ja.finalproject.domain.user.dto.HobbyCategoryDto;
import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.mapper.UserSqlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserSqlMapper userSqlMapper;

    public UserDto getUserByUserIdAndPassword(UserDto userDto) {
        return userSqlMapper.findByUserIdAndPassword(userDto);
    }

    // 취미...
    public List<HobbyCategoryDto> getHobbyList(){
        return userSqlMapper.findHobbyCategoryAll();
    }

    // 인증
    public void authenticateMail(String key) {
        userSqlMapper.updateMailAuthComplete(key);
    }

    public boolean existsUserByUserId(String userId) {
        return userSqlMapper.countUserByUserId(userId) > 0;
    }
}
