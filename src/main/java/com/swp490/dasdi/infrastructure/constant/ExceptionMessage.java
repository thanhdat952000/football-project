package com.swp490.dasdi.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {
    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    public static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    public static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    public static final String ERROR_JWT_DECODE = "Error occurred while decode JWT";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String PAGE_NOT_FOUND = "There is no mapping for this URL";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully!";
    public static final String CHANGE_PASSWORD_SUCCESSFULLY = "Change password successfully!";
    public static final String RESET_PASSWORD_SUCCESSFULLY = "Change password successfully!";
    public static final String SEND_OTP_SUCCESSFULLY = "Send OTP successfully!";
    public static final String OTP_VALIDATE = "OTP is validate!";
}
