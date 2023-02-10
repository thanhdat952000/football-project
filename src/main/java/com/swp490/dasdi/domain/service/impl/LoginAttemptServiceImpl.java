package com.swp490.dasdi.domain.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.swp490.dasdi.domain.service.LoginAttemptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String, Integer> loginAttemptCache;

    public LoginAttemptServiceImpl() {
        loginAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(50)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    @Override
    @Transactional
    public void evictUserFromLoginAttemptCache(String email) {
        loginAttemptCache.invalidate(email);
    }

    @Override
    @Transactional
    public void addUserToLoginAttemptCache(String email) {
        int attempts = 0;
        try {
            attempts = loginAttemptCache.get(email) + ATTEMPT_INCREMENT;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(email, attempts);
    }

    @Override
    @Transactional
    public boolean hasExceededMaxAttempts(String email) {
        try {
            return loginAttemptCache.get(email) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
