package com.swp490.dasdi.infrastructure.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Position {

    GK("Thủ môn"),
    CB("Trung vệ"),
    FB("Hậu vệ cánh"),
    CDM("Tiền vệ phòng ngự"),
    CM("Tiền vệ trung tâm"),
    LM_RM("Tiền vệ cánh"),
    CAM("Tiền về tấn công"),
    CF("Tiền đạo trung tâm"),
    LW_RW("Tiền đạo cánh"),
    ST("Tiền đạo cắm");

    private String value;

    public static final Map<String, String> positionMap = new LinkedHashMap<>();

    static {
        Arrays.stream(Position.values()).forEach(position -> positionMap.put(position.name(), position.value));
    }

    public static String of(String positionName) {
        return positionMap.get(positionName);
    }
}
