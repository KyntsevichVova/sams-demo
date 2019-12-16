package com.sams.demo.security;

import com.sams.demo.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.json.Json;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.System.currentTimeMillis;
import static java.util.Base64.getEncoder;
import static java.util.stream.Collectors.toList;

@Component
public class JwtTokenProvider {

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_METADATA = "metadata";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private long expiration;

    public String generateToken(Authentication authentication, User user) {

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());

        String metadata = Json.createObjectBuilder()
                .add(EMAIL, user.getEmail())
                .add(USERNAME, user.getUsername())
                .build()
                .toString();

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ROLES, roles);
        claims.put(CLAIM_METADATA, getEncoder().encodeToString(metadata.getBytes()));

        /*SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());*/

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis() + expiration))
                .setClaims(claims)
                .signWith(HS512, getEncoder().encodeToString(secret.getBytes()))
                //.signWith(HS512, secret)
                //.signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}