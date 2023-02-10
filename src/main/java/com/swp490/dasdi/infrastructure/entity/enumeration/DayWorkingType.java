package com.swp490.dasdi.infrastructure.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum DayWorkingType {
    WEEKDAY, WEEKEND, HOLIDAY;

    private static final Map<DayWorkingType, Integer> dayWorkingTypeIntegerMap = new LinkedHashMap<>();

    static {
        int count = 0;
        for (DayWorkingType dayWorkingType : DayWorkingType.values()) {
            dayWorkingTypeIntegerMap.put(dayWorkingType, count++);
        }
    }

    public static Integer of(DayWorkingType dayWorkingType) {
        return dayWorkingTypeIntegerMap.get(dayWorkingType);
    }
}
