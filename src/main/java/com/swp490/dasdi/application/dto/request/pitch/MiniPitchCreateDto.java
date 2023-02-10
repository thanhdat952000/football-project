package com.swp490.dasdi.application.dto.request.pitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniPitchCreateDto {
    private long pitchTypeId;
    private int quantity;
    private List<CostCreateDto> costs;
}
