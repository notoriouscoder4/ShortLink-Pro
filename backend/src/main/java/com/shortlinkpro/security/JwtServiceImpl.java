package com.shortlinkpro.security;

import com.shortlinkpro.config.JwtProperties;
import com.shortlinkpro.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );

    }

    @Override
    public String generateAccessToken(UserEntity user) {

        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + jwtProperties.getAccessTokenExpiration()
        );

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiry)
                .claim("role", user.getRole().name())
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();

    }

    @Override
    public String generateRefreshToken(UserEntity user) {
        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + jwtProperties.getRefreshTokenExpiration()
        );

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserEntity user) {
        String userName = extractUserName(token);

        return userName.equals(user.getEmail());
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
