package com.swp490.dasdi.application.dto.request.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchCreateDto {
    private Long invitationId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Long pitchId;
    private String time;
}
