package com.swp490.dasdi.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageExceptionConstant {
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String NO_USER_FOUND_BY_ID = "No user found by id: ";
    public static final String NO_USER_FOUND_BY_EMAIL = "No user found for email: ";
    public static final String NO_USER_FOUND_BY_PHONE_NUMBER = "No user found for phoneNumber: ";
    public static final String FOUND_USER_BY_EMAIL = "Returning found user by email: ";
    public static final String PASSWORD_INCORRECT = "Password is incorrect!";
    public static final String PASSWORD_LENGTH = "Password must be longer than 6 characters!";
    public static final String EMAIL_INCORRECT = "Email is incorrect!";
    public static final String PHONE_EMAIL_FORMAT = "Must be a email / phone number!";
    public static final String PHONE_EMAIL_REQUIRED = "Email / phone number are mandatory!";
    public static final String OTP_NOT_VALID = "OTP is incorrect!";
    public static final String OTP_EXPIRE = "OTP is expire!";
}
