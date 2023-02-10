package com.swp490.dasdi.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostResponse {
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourStart;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourEnd;
    private Integer dayWorkingType;
    private Integer price;
}

