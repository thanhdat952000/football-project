package com.swp490.dasdi.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamUpdateDto {
    @NotNull(message = "id is mandatory")
    private Long id;
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
    private List<MultipartFile> images;
}
