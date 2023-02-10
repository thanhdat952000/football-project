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
public class InvitationResponse {
    private Long id;
    private String message;
    private LocalDateTime time;
    private Integer invitationType;
    private Integer status;
    private LocalDateTime createdDate;
    private Long teamId;
    private String teamName;
    private Long teamBossId;
    private String teamLogoUrl;
    private String teamLevel;
    private Long competitorId;
    private String competitorName;
    private String competitorLogoUrl;
    private String competitorLevel;
    private String city;
    private String district;
    private Long cityId;
    private Long districtId;
    private Long pitchId;
    private String pitchName;
    private String pitchType;
}
