package com.shortlinkpro.user.service.impl;

import com.shortlinkpro.exception.ResourceNotFoundException;
import com.shortlinkpro.exception.EmailAlreadyExistsException;
import com.shortlinkpro.user.dto.CreateUserRequest;
import com.shortlinkpro.user.dto.UserResponse;
import com.shortlinkpro.user.entity.UserEntity;
import com.shortlinkpro.user.mapper.UserMapper;
import com.shortlinkpro.user.repository.UserRepository;
import com.shortlinkpro.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "User with email '" + request.getEmail() + "' already exists."
            );
        }

        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

}
