package com.swp490.dasdi.infrastructure.constant;

import static com.swp490.dasdi.infrastructure.constant.PublicPath.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in millisecond
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String DASDI_SYSTEM = "DASDI System";
    public static final String ADMINISTRATION = "DASDI - Administration";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
//    public static final List<String> PUBLIC_URL = Arrays.asList(AUTH_URL, USER_REGISTER_URL, CITY_URL, LIST_PITCH_URL, DETAIL_PITCH_URL, PITCH_TYPE_URL);
	public static final List<String> PUBLIC_URL = Arrays.asList("**");
}
