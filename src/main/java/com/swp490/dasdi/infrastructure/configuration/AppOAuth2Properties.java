package com.swp490.dasdi.infrastructure.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
public class AppOAuth2Properties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Auth {
        private String secret;
        private long tokenExpirationMsec;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }
}
