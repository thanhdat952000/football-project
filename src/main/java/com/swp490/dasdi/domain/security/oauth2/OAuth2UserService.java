package com.swp490.dasdi.domain.security.oauth2;

import com.swp490.dasdi.domain.exception.OAuth2AuthenticationProcessingException;
import com.swp490.dasdi.domain.security.userinfo.OAuth2UserInfo;
import com.swp490.dasdi.domain.security.UserPrincipal;
import com.swp490.dasdi.domain.service.EmailService;
import com.swp490.dasdi.domain.service.RoleService;
import com.swp490.dasdi.infrastructure.constant.UserStatus;
import com.swp490.dasdi.infrastructure.entity.User;
import com.swp490.dasdi.infrastructure.entity.enumeration.AuthProvider;
import com.swp490.dasdi.infrastructure.reposiotory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return this.processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws MessagingException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(oAuth2UserInfo.getEmail(), UserStatus.WORKING));
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getAuthProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()))) {
                throw new OAuth2AuthenticationProcessingException();
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) throws MessagingException {
        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .fullName(oAuth2UserInfo.getName())
                .avatarUrl(oAuth2UserInfo.getImageUrl())
                .joinDate(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .deleteFlag(UserStatus.WORKING)
                .authProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()))
                .role(roleService.getRoleUser())
                .build();
        emailService.sendWelcomeMail(user.getFullName(), oAuth2UserInfo.getEmail());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatarUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
