package com.swp490.dasdi.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = "id is mandatory")
    private long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private MultipartFile avatar;
    private String birthday;
    private Integer height;
    private Integer weight;
    private String fortePosition;
    private Integer preferredFoot;
}
