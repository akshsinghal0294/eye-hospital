package com.eyehospital.dto.response;

import com.eyehospital.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Long userId;

    private String username;

    private String fullName;

    private Role role;

    private String token;
}