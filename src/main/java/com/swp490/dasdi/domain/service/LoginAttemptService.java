package com.swp490.dasdi.domain.service;

public interface LoginAttemptService {

    void evictUserFromLoginAttemptCache(String email);

    void addUserToLoginAttemptCache(String email);

    boolean hasExceededMaxAttempts(String email);
}
