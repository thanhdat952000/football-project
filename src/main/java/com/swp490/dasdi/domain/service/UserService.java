package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.auth.AuthResetPasswordDto;
import com.swp490.dasdi.application.dto.request.user.UserChangePasswordDto;
import com.swp490.dasdi.application.dto.request.user.UserRegisterDto;
import com.swp490.dasdi.application.dto.request.user.UserUpdateDto;
import com.swp490.dasdi.application.dto.response.PositionResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    List<UserResponse> getAllPaging(String keyword, Pageable pageable);

    UserResponse getById(long id);

    UserResponse getByEmail(String email);

    UserResponse getByPhoneNumber(String phoneNumber);

    void register(UserRegisterDto userRegisterDto) throws MessagingException;

    UserResponse update(long id, UserUpdateDto userUpdateDto);

    List<PositionResponse> getAllPosition();

    void changePassword(long id, UserChangePasswordDto userChangePasswordDto);

    void resetPassword(AuthResetPasswordDto authResetPasswordDto);

    void delete(long id);
}
