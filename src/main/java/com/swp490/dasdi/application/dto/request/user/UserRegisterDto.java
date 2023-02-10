package com.swp490.dasdi.application.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.EMAIL_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.EMAIL_INCORRECT;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.PASSWORD_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_INCORRECT)
    private String email;

    @NotBlank(message = "full name is mandatory")
    private String fullName;

    @NotBlank(message = "password is mandatory")
    @Length(min = 6, message = PASSWORD_LENGTH)
    private String password;
}
