package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.auth.AuthLoginDto;
import com.swp490.dasdi.application.dto.request.auth.AuthResetPasswordDto;
import com.swp490.dasdi.application.dto.request.auth.AuthSendOtpDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.security.CurrentUser;
import com.swp490.dasdi.domain.security.UserPrincipalDetailService;
import com.swp490.dasdi.domain.security.UserPrincipal;
import com.swp490.dasdi.domain.service.SendOTPService;
import com.swp490.dasdi.domain.service.UserService;
import com.swp490.dasdi.infrastructure.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;

import static com.swp490.dasdi.infrastructure.constant.ExceptionMessage.*;
import static com.swp490.dasdi.infrastructure.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserPrincipalDetailService userPrincipalDetailService;
    private final SendOTPService sendOTPService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid AuthLoginDto authLoginDto) {
        this.authenticate(authLoginDto);
        UserPrincipal userPrincipal = (UserPrincipal) userPrincipalDetailService.loadUserByUsername(authLoginDto.getEmail());
        HttpHeaders jwtHeaders = this.setHttpHeaders(userPrincipal);
        UserResponse userResponse = userService.getByEmail(authLoginDto.getEmail());
        return new ResponseEntity<>(userResponse, jwtHeaders, HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserResponse userResponse = userService.getById(userPrincipal.getUser().getId());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody @Valid AuthSendOtpDto authSendOtpDto) {
        sendOTPService.sendOTP(authSendOtpDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, SEND_OTP_SUCCESSFULLY);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<HttpResponse> validateOTP(@RequestBody @Valid AuthSendOtpDto authSendOtpDto) {
        sendOTPService.validateOTP(authSendOtpDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, OTP_VALIDATE);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody @Valid AuthResetPasswordDto authResetPasswordDto) {
        userService.resetPassword(authResetPasswordDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, RESET_PASSWORD_SUCCESSFULLY);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    private void authenticate(AuthLoginDto userLoginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
    }

    private HttpHeaders setHttpHeaders(UserPrincipal userPrincipal) {
        HttpHeaders jwtHeaders = new HttpHeaders();
        jwtHeaders.add(JWT_TOKEN_HEADER, jwtProvider.generateJwtToken(userPrincipal));
        return jwtHeaders;
    }

    private HttpResponse setHttpResponse(HttpStatus httpStatus, String message) {
        return HttpResponse.builder()
                .timeStamp(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .reason(httpStatus.getReasonPhrase().toUpperCase())
                .message(message)
                .build();
    }
}
