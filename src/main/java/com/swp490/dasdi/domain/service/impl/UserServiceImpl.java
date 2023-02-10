package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.auth.AuthResetPasswordDto;
import com.swp490.dasdi.application.dto.request.user.UserChangePasswordDto;
import com.swp490.dasdi.application.dto.request.user.UserRegisterDto;
import com.swp490.dasdi.application.dto.request.user.UserUpdateDto;
import com.swp490.dasdi.application.dto.response.PositionResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.exception.EmailExistException;
import com.swp490.dasdi.domain.exception.PasswordIncorrectException;
import com.swp490.dasdi.domain.exception.UserNotFoundException;
import com.swp490.dasdi.domain.mapper.UserMapper;
import com.swp490.dasdi.domain.service.EmailService;
import com.swp490.dasdi.domain.service.RoleService;
import com.swp490.dasdi.domain.service.UploadFileService;
import com.swp490.dasdi.domain.service.UserService;
import com.swp490.dasdi.infrastructure.constant.UserStatus;
import com.swp490.dasdi.infrastructure.entity.User;
import com.swp490.dasdi.infrastructure.entity.enumeration.AuthProvider;
import com.swp490.dasdi.infrastructure.entity.enumeration.Position;
import com.swp490.dasdi.infrastructure.entity.enumeration.PreferredFoot;
import com.swp490.dasdi.infrastructure.reposiotory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.swp490.dasdi.infrastructure.constant.EmailConstant.EMAIL_PATTERN;
import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UploadFileService uploadFileService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(user -> userMapper.toUserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllPaging(String keyword, Pageable pageable) {
        keyword = StringUtils.isNotBlank(keyword) ? keyword.trim() : StringUtils.EMPTY;
        return userRepository.findAllPaging(keyword, UserStatus.WORKING, pageable).stream()
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getById(long id) {
        User user = this.findById(id);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = this.findByEmail(email);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    @Override
    public UserResponse getByPhoneNumber(String phoneNumber) {
        User user = this.findByPhoneNumber(phoneNumber);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    @Override
    @Transactional
    public void register(UserRegisterDto userRegisterDto) throws MessagingException {
        this.validateNewUserByEmail(userRegisterDto.getEmail());
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .fullName(userRegisterDto.getFullName())
                .password(this.encodePassword(userRegisterDto.getPassword()))
                .joinDate(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .deleteFlag(UserStatus.WORKING)
                .authProvider(AuthProvider.LOCAL)
                .role(roleService.getRoleUser())
                .build();
        userRepository.save(user);
        emailService.sendWelcomeMail(user.getFullName(), userRegisterDto.getEmail());
    }

    @Override
    @Transactional
    public UserResponse update(long id, UserUpdateDto userUpdateDto) {
        User user = this.findById(userUpdateDto.getId());
        if (Objects.nonNull(userUpdateDto.getFullName())) {
            user.setFullName(userUpdateDto.getFullName());
        }
        if (Objects.nonNull(userUpdateDto.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }
        if (Objects.nonNull(userUpdateDto.getAvatar())) {
            user.setAvatarUrl(uploadFileService.uploadImage(userUpdateDto.getAvatar(), UploadFileService.FolderType.USER, user.getEmail()));
        }
        if (Objects.nonNull(userUpdateDto.getBirthday())) {
            user.setBirthday(LocalDate.parse(userUpdateDto.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (Objects.nonNull(userUpdateDto.getHeight())) {
            user.setHeight(userUpdateDto.getHeight());
        }
        if (Objects.nonNull(userUpdateDto.getWeight())) {
            user.setWeight(userUpdateDto.getWeight());
        }
        if (Objects.nonNull(userUpdateDto.getFortePosition())) {
            user.setFortePosition(userUpdateDto.getFortePosition());
        }
        if (Objects.nonNull(userUpdateDto.getPreferredFoot())) {
            user.setPreferredFoot(PreferredFoot.values()[userUpdateDto.getPreferredFoot()]);
        }
        userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    @Override
    @Transactional
    public void changePassword(long id, UserChangePasswordDto userChangePasswordDto) {
        User user = this.findById(id);
        if (passwordEncoder.matches(userChangePasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(this.encodePassword(userChangePasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new PasswordIncorrectException(PASSWORD_INCORRECT);
        }
    }

    @Override
    @Transactional
    public void resetPassword(AuthResetPasswordDto authResetPasswordDto) {
        User user;
        if (Pattern.matches(EMAIL_PATTERN, authResetPasswordDto.getEmailOrPhoneNumber())) {
            user = this.findByEmail(authResetPasswordDto.getEmailOrPhoneNumber());
        } else {
            user = this.findByPhoneNumber(authResetPasswordDto.getEmailOrPhoneNumber());
        }
        user.setPassword(this.encodePassword(authResetPasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        User user = this.findById(id);
        user.setDeleteFlag(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public List<PositionResponse> getAllPosition() {
        List<PositionResponse> positions = Position.positionMap.entrySet().stream()
                .map(entry -> PositionResponse.builder()
                        .id(entry.getKey())
                        .name(entry.getValue())
                        .build())
                .collect(Collectors.toList());
        return positions;
    }

    private User findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id, UserStatus.WORKING))
                .orElseThrow(() -> new UserNotFoundException(NO_USER_FOUND_BY_ID + id));
    }

    private User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email, UserStatus.WORKING))
                .orElseThrow(() -> new UserNotFoundException(NO_USER_FOUND_BY_EMAIL + email));
    }

    private User findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber, UserStatus.WORKING))
                .orElseThrow(() -> new UserNotFoundException(NO_USER_FOUND_BY_PHONE_NUMBER + phoneNumber));
    }

    private void validateNewUserByEmail(String email) {
        User userByNewEmail = userRepository.findByEmail(email, UserStatus.WORKING);
        if (Objects.nonNull(userByNewEmail)) {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
