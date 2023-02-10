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
public class BookingResponse {
    private Long id;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String note;
    private Integer payment;
    private Integer price;
    private Integer status;
    private Long pitchId;
    private String pitchName;
    private String pitchLogoUrl;
    private String pitchCity;
    private String pitchType;
    private String userName;
    private String userAvatarUrl;
}
