package com.swp490.dasdi.application.dto.request.pitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PitchCreateDto {
    private String name;
    private String description;
    private MultipartFile logo;
    private String hourStart;
    private String hourEnd;
    private String addressDetail;
    private String phoneNumber;
    private String facebook;
    private String mail;
    private Long districtId;
    private String latitude;
    private String longitude;
    private Long ownerId;
    private List<MultipartFile> images;
    private List<MiniPitchCreateDto> miniPitches;
}
