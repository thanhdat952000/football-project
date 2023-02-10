package com.swp490.dasdi.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRegisterResponse {
    private Long id;
    private Long playerId;
    private String playerName;
    private String playerAvatarUrl;
    private String playerFortePosition;
    private Integer playerHeight;
    private Integer playerWeight;
    private String message;
    @JsonFormat(pattern = "dd/MM/yyy HH:mm")
    private LocalDateTime createdDate;
    private Boolean approvalFlag;
}
