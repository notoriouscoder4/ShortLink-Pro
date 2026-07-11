package com.shortlinkpro.user.dto;

import com.shortlinkpro.common.enums.Role;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Role role;
    private Boolean enabled;
    private Long id;
    private String name;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}
