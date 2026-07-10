package com.shortlinkpro.user.mapper;

import com.shortlinkpro.user.dto.CreateUserRequest;
import com.shortlinkpro.user.dto.UserResponse;
import com.shortlinkpro.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(CreateUserRequest request);

    UserResponse toResponse(UserEntity entity);
}
