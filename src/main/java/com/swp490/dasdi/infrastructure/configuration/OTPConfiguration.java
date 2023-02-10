package com.swp490.dasdi.infrastructure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class OTPConfiguration {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
