package com.shortlinkpro.user.controller;

import com.shortlinkpro.common.constants.ApiConstants;
import com.shortlinkpro.common.reponse.ApiResponse;
import com.shortlinkpro.user.dto.CreateUserRequest;
import com.shortlinkpro.user.dto.UserResponse;
import com.shortlinkpro.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_V1 + "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        UserResponse response = userService.createUser(request);

        return ApiResponse.success(
                "User created successfully.",
                response);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {

        return ApiResponse.success(
                "User fetched successfully.",
                userService.getUserById(id)
        );
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {

        return ApiResponse.success(
                "Users fetched successfully.",
                userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ApiResponse.success(
                "User deleted successfully.",
                "SUCCESS");
    }

}
