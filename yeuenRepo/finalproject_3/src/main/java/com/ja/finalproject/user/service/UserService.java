package com.ja.finalproject.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ja.finalproject.dto.HobbyCategoryDto;
import com.ja.finalproject.dto.MailAuthDto;
import com.ja.finalproject.dto.UserDto;
import com.ja.finalproject.dto.UserHobbyDto;
import com.ja.finalproject.user.mapper.UserSqlMapper;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
public class UserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserSqlMapper userSqlMapper;

    public void register(UserDto userDto, List<Integer> hobbyIdList){
        //... 
        userSqlMapper.createUser(userDto);

        int lastUserPk = userDto.getId();

        for(int hobbyId : hobbyIdList) {
            UserHobbyDto userHobbyDto = new UserHobbyDto();
            userHobbyDto.setUser_id(lastUserPk);
            userHobbyDto.setHobby_id(hobbyId);
            userSqlMapper.createUserHobby(userHobbyDto);
        }

        // 메일 보내기

        // 인증 번호 생성
        String authKey = UUID.randomUUID().toString();

        // DB 저장
        MailAuthDto mailAuthDto = new MailAuthDto();
        mailAuthDto.setUser_id(lastUserPk);
        mailAuthDto.setAuth_key(authKey);
        userSqlMapper.createMailAuth(mailAuthDto);

        // 쓰레드 생성 및 실행
        new MailSendThread(javaMailSender, userDto.getEmail(), authKey).start();
        
    }

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

@AllArgsConstructor
class MailSendThread extends Thread{

    private JavaMailSender javaMailSender;
    private String toMailAddress;
    private String authKey;

    @Override
    public void run() {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = 
                new MimeMessageHelper(mimeMessage, true, "utf-8");

            mimeMessageHelper.setSubject("[FP] 회원 가입을 축하드립니다.");

            String text = "회원 가입을 축하드립니다.<br>";
            text += "아래 링크를 클릭하셔서 인증완료 후 로그인 가능합니다.<br>";
            text += "<a href='http://localhost:8888/user/mailAuthProcess?key="+ authKey +"'>인증하기</a>";

            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setTo(toMailAddress);
            mimeMessageHelper.setFrom("admin", "최종 예제 관리자");

            javaMailSender.send(mimeMessage);
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}