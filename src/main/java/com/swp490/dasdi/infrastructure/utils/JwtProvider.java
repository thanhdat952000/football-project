package com.swp490.dasdi.infrastructure.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.swp490.dasdi.domain.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.swp490.dasdi.infrastructure.constant.SecurityConstant.*;

@Component
public class JwtProvider {

    @Value("${jwt.auth.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        String[] claims = this.getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuer(DASDI_SYSTEM)
                .withAudience(ADMINISTRATION).withIssuedAt(new Date())
                .withSubject(userPrincipal.getUser().getEmail())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(UserPrincipal userPrincipal, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthToken;
    }

    public String getSubject(String token) {
        JWTVerifier verifier = this.getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = this.getJWTVerifier();
        return StringUtils.isNotEmpty(username) && isNotTokenExpired(verifier, token);
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();
        userPrincipal.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
        return authorities.toArray(new String[0]);
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = this.getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(DASDI_SYSTEM).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private boolean isNotTokenExpired(JWTVerifier verifier, String token) {
        Date tokenExpiredDate = verifier.verify(token).getExpiresAt();
        return tokenExpiredDate.after(new Date());
    }
}
