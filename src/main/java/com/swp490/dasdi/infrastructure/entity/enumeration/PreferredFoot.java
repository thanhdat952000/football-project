package com.swp490.dasdi.infrastructure.entity.enumeration;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public enum PreferredFoot {
    RIGHT, LEFT, BOTH;

    private static final Map<PreferredFoot, Integer> preferredFootMap = new LinkedHashMap<>();

    static {
        int count = 0;
        for (PreferredFoot preferredFoot : PreferredFoot.values()) {
            preferredFootMap.put(preferredFoot, count++);
        }
    }

    public static Integer of(PreferredFoot preferredFoot) {
        return preferredFootMap.get(preferredFoot);
    }
}
