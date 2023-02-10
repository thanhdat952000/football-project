package com.swp490.dasdi.application.dto.request.pitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPitchCreateDto {
    private int star;
    private String content;
    private Long pitchId;
    private Long userId;
}
