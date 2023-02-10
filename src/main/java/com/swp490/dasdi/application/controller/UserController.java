package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.user.UserChangePasswordDto;
import com.swp490.dasdi.application.dto.request.user.UserRegisterDto;
import com.swp490.dasdi.application.dto.request.user.UserUpdateDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.application.dto.response.PositionResponse;
import com.swp490.dasdi.application.dto.response.RoleResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.service.RoleService;
import com.swp490.dasdi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.swp490.dasdi.infrastructure.constant.ExceptionMessage.CHANGE_PASSWORD_SUCCESSFULLY;
import static com.swp490.dasdi.infrastructure.constant.ExceptionMessage.USER_DELETED_SUCCESSFULLY;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    //    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> userResponses = userService.getAll();
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAllPaging(@RequestParam(required = false) String keyword, Pageable pageable) {
        List<UserResponse> userResponses = userService.getAllPaging(keyword, pageable);
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) {
        UserResponse userResponse = userService.getById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/positions")
    public ResponseEntity<List<PositionResponse>> getAllPositions() {
        List<PositionResponse> positionResponse = userService.getAllPosition();
        return new ResponseEntity<>(positionResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws MessagingException {
        userService.register(userRegisterDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "User create successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @ModelAttribute @Valid UserUpdateDto userUpdateDto) {
        UserResponse userResponse = userService.update(id, userUpdateDto);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<HttpResponse> changePassword(@PathVariable long id, @RequestBody @Valid UserChangePasswordDto userChangePasswordDto) {
        userService.changePassword(id, userChangePasswordDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, CHANGE_PASSWORD_SUCCESSFULLY);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable long id) throws IOException {
        userService.delete(id);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, USER_DELETED_SUCCESSFULLY);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
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
