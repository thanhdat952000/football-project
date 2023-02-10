package com.swp490.dasdi.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRegisterApprovalDto {
    private Long teamRegisterId;
    private Long teamId;
    private Long playerId;
}
