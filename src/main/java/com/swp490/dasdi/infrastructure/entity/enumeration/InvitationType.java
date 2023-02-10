package com.swp490.dasdi.infrastructure.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum InvitationType {
    FUNNY,
    FRIENDSHIP,
    ALL_IN;

    public static final Map<Integer, String> invitationTypeResponseMap = new LinkedHashMap<>();
    private static final Map<InvitationType, Integer> invitationTypeMap = new LinkedHashMap<>();

    static {
        int count = 0;
        for (InvitationType invitationType : InvitationType.values()) {
            invitationTypeMap.put(invitationType, count++);
        }
        invitationTypeResponseMap.put(0, "Giao lưu 5-5 vui vẻ");
        invitationTypeResponseMap.put(1, "Giao lưu 7-3");
        invitationTypeResponseMap.put(2, "Giao lưu trả hết");
    }

    public static Integer of(InvitationType invitationType) {
        return invitationTypeMap.get(invitationType);
    }
}
