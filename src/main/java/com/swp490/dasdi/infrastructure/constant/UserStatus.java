package com.swp490.dasdi.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStatus {
    public static final Boolean ACTIVE = true;
    public static final Boolean LOCK = false;
    public static final Boolean DELETED = true;
    public static final Boolean WORKING = false;
}
