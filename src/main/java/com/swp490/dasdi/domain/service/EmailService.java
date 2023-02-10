package com.swp490.dasdi.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Transactional
    public void sendWelcomeMail(String fullName, String toEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(EMAIL_SUBJECT_WELCOME);
        helper.setFrom(FROM_EMAIL);
        helper.setTo(toEmail);
        boolean html = true;
        helper.setText(contentWelcomeTextMail(fullName), html);
        mailSender.send(message);
    }

    @Transactional
    public void sendOTP(String fullName, String otp, String toEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(EMAIL_SUBJECT_OTP);
        helper.setFrom(FROM_EMAIL);
        helper.setTo(toEmail);
        boolean html = true;
        helper.setText(contentOtpTextMail(fullName, otp), html);
        mailSender.send(message);
    }
}
