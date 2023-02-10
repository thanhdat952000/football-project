package com.swp490.dasdi.infrastructure.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum InvitationStatus {
    OUT_OF_DATE,
    ACTIVATING,
    WAITING_FOR_APPROVE,
    IN_PROGRESS,
    DONE;

    private static final Map<InvitationStatus, Integer> invitationStatusMap = new LinkedHashMap<>();

    static {
        int count = 0;
        for (InvitationStatus invitationType : InvitationStatus.values()) {
            invitationStatusMap.put(invitationType, count++);
        }
    }

    public static Integer of(InvitationStatus invitationStatus) {
        return invitationStatusMap.get(invitationStatus);
    }
}
