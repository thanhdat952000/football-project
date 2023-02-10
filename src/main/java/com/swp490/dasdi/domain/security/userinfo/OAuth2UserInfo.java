package com.swp490.dasdi.domain.security.userinfo;

import com.swp490.dasdi.domain.exception.OAuth2AuthenticationProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class OAuth2UserInfo {
    private static final String GOOGLE_PROVIDER = "google";
    private static final String FACEBOOK_PROVIDER = "facebook";
    protected Map<String, Object> attributes;

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        OAuth2UserInfo oAuth2UserInfo = null;
        switch (registrationId) {
            case GOOGLE_PROVIDER:
                oAuth2UserInfo = new GoogleOAuth2UserInfo(attributes);
                break;
            case FACEBOOK_PROVIDER:
                oAuth2UserInfo = new FacebookOAuth2UserInfo(attributes);
                break;
            default:
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
        return oAuth2UserInfo;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
