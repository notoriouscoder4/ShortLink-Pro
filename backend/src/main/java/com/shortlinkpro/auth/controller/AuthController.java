package com.shortlinkpro.auth.controller;

import com.shortlinkpro.auth.dto.LoginRequest;
import com.shortlinkpro.auth.dto.LoginResponse;
import com.shortlinkpro.auth.dto.RegisterRequest;
import com.shortlinkpro.auth.service.AuthService;
import com.shortlinkpro.common.constants.ApiConstants;
import com.shortlinkpro.common.reponse.ApiResponse;
import com.shortlinkpro.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(
                "User registered successfully.",
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(
                "Login successful.",
                authService.login(request)
        );
    }
}
