package com.rent.rentservice.mail.service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.rent.rentservice.mail.domain.AuthNum;
import com.rent.rentservice.mail.exception.InvaildAuthCheckException;
import com.rent.rentservice.mail.repository.MailRepository;
import com.rent.rentservice.mail.request.AuthCheckForm;
import com.rent.rentservice.mail.request.SendMailForm;
import com.rent.rentservice.user.exception.InvalidEmailPatternException;
import com.rent.rentservice.util.common.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.channels.Pipe;

/**
 * @description Mail 전송 및 인증 서비스 레이어
 * @author 김승진
 * @since 2023.01.20
 */

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailRepository mailRepository;

    private final JavaMailSender javaMailSender;

    // 메세지 생성
    private MimeMessage sendAuthMail(String toEmail) throws Exception {

        final String authNumber = CommonUtil.createAuthNumber();
        MimeMessage  message = javaMailSender.createMimeMessage();

        //보내는 대상
        message.addRecipients(RecipientType.TO, toEmail);
        //제목
        message.setSubject("이메일 인증 테스트");

        // 메일 내용
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 RENTIVE입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>이메일 인증을 위한 아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= authNumber+"</strong><div><br/> ";
        msgg+= "</div>";

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("rentivemail@gmail.com","RENTIVE"));

        // 인증 정보 객체 만들고
        AuthNum authNum = AuthNum.builder()
                .email(toEmail)
                .authNum(authNumber)
                .build();


        // 인증 정보 저장
        mailRepository.save(authNum);

        return message;
    }

    // 메일 전송
    public void sendMessage(SendMailForm request) throws Exception {

        // 이메일 형식 체크
        if(!CommonUtil.isValidEmail(request.getEmail())) {
            throw new InvalidEmailPatternException();
        }

        // 메세지 생성
        MimeMessage message = sendAuthMail(request.getEmail());

        // 메일 전송
        javaMailSender.send(message);
    }


    // 인증번호 체크
    public void authNumCheck(AuthCheckForm request) {

        // 이메일 형식 체크
        if(!CommonUtil.isValidEmail(request.getEmail())) {
            throw new InvalidEmailPatternException();
        }

        if(!mailRepository.existsByEmailAndAuthNum(request.getEmail(), request.getAuthNum())) {
            throw new InvaildAuthCheckException();
        }
    }
}
