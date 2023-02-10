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
public class ReviewResponse {
    private Integer star;
    private String content;
    private String username;
    private String userAvatarUrl;
    private LocalDateTime createDate;
}
