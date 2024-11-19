package com.ja.finalproject.global.mailsender;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class MailSendService {

    private JavaMailSender javaMailSender;

    public void sendMail(String toMailAddress, String authKey) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            mimeMessageHelper.setSubject("[FP] 회원 가입을 축하드립니다.");
            String text = "회원 가입을 축하드립니다.<br>"
                    + "아래 링크를 클릭하셔서 인증완료 후 로그인 가능합니다.<br>"
                    + "<a href='http://localhost:8888/user/mailAuthProcess?key=" + authKey + "'>인증하기</a>";
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setTo(toMailAddress);
            mimeMessageHelper.setFrom("admin", "최종 예제 관리자");

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("메일 전송 실패: {}", e.getMessage(), e);
        }
    }

}