package com.swp490.dasdi.infrastructure.configuration;

import com.swp490.dasdi.domain.filter.JwtAccessDeniedHandler;
import com.swp490.dasdi.domain.filter.JwtAuthenticationEntryPoint;
import com.swp490.dasdi.domain.filter.JwtAuthorizationFilter;
import com.swp490.dasdi.domain.security.UserPrincipalDetailService;
import com.swp490.dasdi.domain.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.swp490.dasdi.domain.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.swp490.dasdi.domain.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.swp490.dasdi.domain.security.oauth2.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.swp490.dasdi.infrastructure.constant.SecurityConstant.PUBLIC_URL;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserPrincipalDetailService userPrincipalDetailService;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userPrincipalDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                    .and()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .authorizeRequests()
                    .antMatchers(PUBLIC_URL.toArray(new String[0]))
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(oAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
