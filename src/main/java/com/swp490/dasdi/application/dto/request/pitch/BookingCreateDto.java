package com.swp490.dasdi.application.dto.request.pitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateDto {
    private String timeEnd;
    private String timeStart;
    private String note;
    private Integer payment;
    private Integer price;
    private Long userId;
    private Long miniPitchId;
}
