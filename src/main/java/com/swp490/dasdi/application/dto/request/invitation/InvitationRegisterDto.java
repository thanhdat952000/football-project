package com.swp490.dasdi.application.dto.request.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRegisterDto {
    private Long invitationId;
    private Long competitorId;
}
