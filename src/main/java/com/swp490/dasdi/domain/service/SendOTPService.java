package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.auth.AuthSendOtpDto;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.exception.ExpireOTPException;
import com.swp490.dasdi.domain.exception.OTPNotValidException;
import com.swp490.dasdi.domain.exception.SendOTPException;
import com.swp490.dasdi.infrastructure.configuration.OTPConfiguration;
import com.swp490.dasdi.infrastructure.constant.EmailConstant;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.EMAIL_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.EmailConstant.OTP_SENT;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.OTP_EXPIRE;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.OTP_NOT_VALID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendOTPService {
    private final OTPConfiguration otpConfiguration;
    private final UserService userService;
    private final EmailService emailService;

    private Map<String, String> otpMap = new LinkedHashMap<>();
    private long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

    @PostConstruct
    public void initTwilio() {
        Twilio.init(otpConfiguration.getAccountSid(), otpConfiguration.getAuthToken());
    }

    @Transactional
    public void sendOTP(AuthSendOtpDto authSendOtpDto) {
        String otp = this.generateOTP();
        long otpRequestedTime = System.currentTimeMillis();
        log.info(otp);
        if (Pattern.matches(EMAIL_PATTERN, authSendOtpDto.getEmailOrPhoneNumber())) {
            String email = authSendOtpDto.getEmailOrPhoneNumber();
            this.sendOTPByEmail(email, otp, otpRequestedTime);
        } else {
            this.sendOTPByPhone(authSendOtpDto.getEmailOrPhoneNumber(), otp, otpRequestedTime);
        }
    }

    @Transactional
    public void validateOTP(AuthSendOtpDto authSendOtpDto) {
        if (!authSendOtpDto.getOtp().equals(otpMap.get(authSendOtpDto.getEmailOrPhoneNumber()))) {
            throw new OTPNotValidException(OTP_NOT_VALID);
        }
        if (!isExpireOtp(Long.parseLong(otpMap.get(authSendOtpDto.getOtp())))) {
            throw new ExpireOTPException(OTP_EXPIRE);
        }
    }

    private void sendOTPByEmail(String email, String otp, long otpRequestedTime) {
        try {
            UserResponse userResponse = userService.getByEmail(email);
            emailService.sendOTP(userResponse.getFullName(), otp, email); // Send otp by email
            otpMap.put(email, otp);
            otpMap.put(otp, String.valueOf(otpRequestedTime));
            log.info(OTP_SENT + email);
        } catch (MessagingException e) {
            throw new SendOTPException(e.getMessage());
        }
    }

    private void sendOTPByPhone(String phoneNumber, String otp, long otpRequestedTime) {
        try {
            UserResponse userResponse = userService.getByPhoneNumber(phoneNumber);
            PhoneNumber to = new PhoneNumber(this.checkPhoneNumberStartWith0(phoneNumber));
            PhoneNumber from = new PhoneNumber(otpConfiguration.getTrialNumber());
            String otpMessage = EmailConstant.contentOtpTextPhone(userResponse.getFullName(), otp);
            Message message = Message.creator(to, from, otpMessage).create(); // Send otp by phone number
            otpMap.put(phoneNumber, otp);
            otpMap.put(otp, String.valueOf(otpRequestedTime));
            log.info(OTP_SENT + phoneNumber);
        } catch (RuntimeException e) {
            throw new SendOTPException(e.getMessage());
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    private String checkPhoneNumberStartWith0(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return phoneNumber.replaceFirst("0", "+84");
        }
        return phoneNumber;
    }

    private boolean isExpireOtp(long otpRequestedTime) {
        return otpRequestedTime + OTP_VALID_DURATION > System.currentTimeMillis();
    }
}
