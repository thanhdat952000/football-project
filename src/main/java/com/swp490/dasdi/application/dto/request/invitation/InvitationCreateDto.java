package com.swp490.dasdi.application.dto.request.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationCreateDto {
    private String message;
    private String time;
    private Integer invitationType;
    private Long teamId;
    private Long cityId;
    private Long districtId;
    private Long pitchId;
}
