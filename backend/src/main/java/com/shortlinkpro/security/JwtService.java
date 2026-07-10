package com.shortlinkpro.security;

import com.shortlinkpro.user.entity.UserEntity;

public interface JwtService {

    String generateAccessToken(UserEntity user);

    String generateRefreshToken(UserEntity user);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserEntity user);

}
