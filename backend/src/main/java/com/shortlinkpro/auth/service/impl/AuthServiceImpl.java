package com.shortlinkpro.auth.service.impl;

import com.shortlinkpro.auth.dto.LoginRequest;
import com.shortlinkpro.auth.dto.LoginResponse;
import com.shortlinkpro.auth.dto.RegisterRequest;
import com.shortlinkpro.auth.service.AuthService;
import com.shortlinkpro.common.enums.Role;
import com.shortlinkpro.exception.EmailAlreadyExistsException;
import com.shortlinkpro.exception.ResourceNotFoundException;
import com.shortlinkpro.security.JwtService;
import com.shortlinkpro.user.dto.UserResponse;
import com.shortlinkpro.user.entity.UserEntity;
import com.shortlinkpro.user.mapper.UserMapper;
import com.shortlinkpro.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "User with email '" + request.getEmail() + "' already exists."
            );
        }

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password.");
        }

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();

    }
}
