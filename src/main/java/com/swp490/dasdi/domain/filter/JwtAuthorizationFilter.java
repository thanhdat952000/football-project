package com.swp490.dasdi.domain.filter;

import com.swp490.dasdi.domain.security.UserPrincipal;
import com.swp490.dasdi.domain.security.UserPrincipalDetailService;
import com.swp490.dasdi.infrastructure.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.swp490.dasdi.infrastructure.constant.SecurityConstant.OPTIONS_HTTP_METHOD;
import static com.swp490.dasdi.infrastructure.constant.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserPrincipalDetailService userPrincipalDetailService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String email = jwtProvider.getSubject(token);
            UserPrincipal userPrincipal = userPrincipalDetailService.getByEmail(email);
            if (jwtProvider.isTokenValid(email, token) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);
                Authentication authentication = jwtProvider.getAuthentication(userPrincipal, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        }
    }
}
