package com.swp490.dasdi.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PitchResponse {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourStart;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourEnd;
    private Integer status;
    @JsonFormat(pattern = "dd/MM/yyy HH:mm")
    private LocalDateTime createdDate;
    private AddressResponse address;
    private String owner;
    private List<MiniPitchResponse> miniPitches;
    private List<String> imageUrls;
    private List<ReviewResponse> reviews;
    private CostRangeResponse costRange;
    private ReviewStarResponse reviewStar;
    private List<String> pitchTypes;
}
