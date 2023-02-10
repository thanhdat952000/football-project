package com.swp490.dasdi.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.PASSWORD_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordDto {

    @NotNull(message = "id is mandatory")
    private Long id;

    @NotBlank(message = "currentPassword is mandatory")
    @Length(min = 6, message = PASSWORD_LENGTH)
    private String currentPassword;

    @NotBlank(message = "currentPassword is mandatory")
    @Length(min = 6, message = PASSWORD_LENGTH)
    private String newPassword;
}
