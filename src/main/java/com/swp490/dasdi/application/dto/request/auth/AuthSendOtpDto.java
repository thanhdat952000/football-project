package com.swp490.dasdi.application.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.EMAIL_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.EmailConstant.PHONE_NUMBER_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.PHONE_EMAIL_FORMAT;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.PHONE_EMAIL_REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSendOtpDto {
    @NotBlank(message = PHONE_EMAIL_REQUIRED)
    @Pattern(regexp = EMAIL_PATTERN + "|" + PHONE_NUMBER_PATTERN, message = PHONE_EMAIL_FORMAT)
    private String emailOrPhoneNumber;
    private String otp;
}
