package com.shortlinkpro.user.service;

import com.shortlinkpro.user.dto.CreateUserRequest;
import com.shortlinkpro.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}
