package com.swp490.dasdi.application.dto.request.team;

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
public class TeamCreateDto {
    private String name;
    private String description;
    private MultipartFile logo;
    private Long levelId;
    private String addressDetail;
    private String phoneNumber;
    private String facebook;
    private String mail;
    private Long districtId;
    private String latitude;
    private String longitude;
    private Boolean recruitFlag;
    private Long homePitchId;
    private Long teamBossId;
    private List<MultipartFile> images;
}
