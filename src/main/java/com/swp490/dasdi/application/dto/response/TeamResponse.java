package com.swp490.dasdi.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Boolean status;
    private Boolean recruitFlag;
    @JsonFormat(pattern = "dd/MM/yyy HH:mm")
    private LocalDateTime createdDate;
    private Long levelId;
    private String level;
    private AddressResponse address;
    private Long homePitchId;
    private String homePitchName;
    private Long teamBossId;
    private String teamBoss;
    private List<UserResponse> players;
    private List<TeamRegisterResponse> teamRegisters;
    private List<String> imageUrls;
    private List<MatchResponse> matches;
}
