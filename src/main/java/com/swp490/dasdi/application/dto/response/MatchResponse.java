package com.swp490.dasdi.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
    private Long id;
    private Long homeTeamId;
    private String homeTeamName;
    private String homeTeamLogoUrl;
    private String homeTeamLevel;
    private Long awayTeamId;
    private String awayTeamName;
    private String awayTeamLogoUrl;
    private String awayTeamLevel;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Long pitchId;
    private String pitchName;
    private LocalDateTime time;
    private Integer status;
    private LocalDateTime createdDate;
}
