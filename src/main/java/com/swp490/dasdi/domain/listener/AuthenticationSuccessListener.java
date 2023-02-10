package com.swp490.dasdi.domain.listener;

import com.swp490.dasdi.domain.service.LoginAttemptService;
import com.swp490.dasdi.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }
}
