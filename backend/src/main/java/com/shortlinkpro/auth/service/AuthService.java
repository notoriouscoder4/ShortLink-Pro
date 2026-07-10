package com.shortlinkpro.auth.service;

import com.shortlinkpro.auth.dto.LoginRequest;
import com.shortlinkpro.auth.dto.LoginResponse;
import com.shortlinkpro.auth.dto.RegisterRequest;
import com.shortlinkpro.user.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
