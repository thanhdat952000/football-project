package com.swp490.dasdi.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicPath {
    public static final String AUTH_URL = "/auth/**";
    public static final String USER_REGISTER_URL = "/user/register";
    public static final String CITY_URL = "/city/**";
    public static final String LIST_PITCH_URL = "/pitch/filter";
    public static final String DETAIL_PITCH_URL = "/pitch/";
    public static final String PITCH_TYPE_URL = "/pitch/type";

}
