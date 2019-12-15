package com.sams.demo.security;

import com.sams.demo.model.entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.System.currentTimeMillis;

@Component
public class JwtTokenProvider {

    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLES = "roles";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private long expiration;

    public String generateToken(Authentication authentication, User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_EMAIL, user.getEmail());
        claims.put(CLAIM_USERNAME, user.getUsername());
        claims.put(CLAIM_ROLES, authentication.getAuthorities());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis() + expiration))
                .setClaims(claims)
                .signWith(HS512, secret)
                .compact();
    }
}