package com.swp490.dasdi.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
    @JsonFormat(pattern = "dd/MM/yyy")
    private LocalDate birthday;
    private Integer height;
    private Integer weight;
    private String fortePosition;
    private Integer preferredFoot;
    @JsonFormat(pattern = "dd/MM/yyy HH:mm")
    private LocalDateTime joinDate;
    private Boolean status;
    private RoleResponse role;
}
