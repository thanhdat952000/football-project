package com.swp490.dasdi.application.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.EMAIL_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.EMAIL_INCORRECT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDto {

    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_INCORRECT)
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;
}
