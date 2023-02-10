package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.security.CurrentUser;
import com.swp490.dasdi.domain.security.UserPrincipal;
import com.swp490.dasdi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CurrentUserController {
    private final UserService userService;

    @GetMapping("/current-user")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserResponse userResponse = userService.getById(userPrincipal.getUser().getId());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
