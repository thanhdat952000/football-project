package com.swp490.dasdi.domain.security;

import com.swp490.dasdi.domain.exception.UserNotFoundException;
import com.swp490.dasdi.domain.service.LoginAttemptService;
import com.swp490.dasdi.infrastructure.constant.UserStatus;
import com.swp490.dasdi.infrastructure.entity.User;
import com.swp490.dasdi.infrastructure.reposiotory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.swp490.dasdi.infrastructure.constant.MessageExceptionConstant.NO_USER_FOUND_BY_EMAIL;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.findByEmail(email);
        this.validateLoginAttempt(user);
        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user); // save last login date
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserPrincipal getByEmail(String email) {
        User user = findByEmail(email);
        return UserPrincipal.create(user);
    }

    private User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email, UserStatus.WORKING)).orElseThrow(() -> new UserNotFoundException(NO_USER_FOUND_BY_EMAIL + email));
    }

    private void validateLoginAttempt(User user) {
        if (user.getStatus() == UserStatus.ACTIVE) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getEmail())) {
                user.setStatus(UserStatus.LOCK);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }
}
