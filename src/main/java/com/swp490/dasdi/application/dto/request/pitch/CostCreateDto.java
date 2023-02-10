package com.swp490.dasdi.application.dto.request.pitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostCreateDto {
    private String hourStart;
    private String hourEnd;
    private int dayWorkingType;
    private int price;
}
