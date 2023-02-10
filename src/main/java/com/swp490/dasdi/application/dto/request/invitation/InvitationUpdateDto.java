package com.swp490.dasdi.application.dto.request.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationUpdateDto {
    @NotNull(message = "id is mandatory")
    private Long id;
    private String message;
    private String time;
    private Integer invitationType;
    private Long teamId;
    private Long districtId;
    private Long cityId;
    private Long pitchId;
}
