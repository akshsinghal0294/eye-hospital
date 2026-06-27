package com.eyehospital.service;

import com.eyehospital.dto.request.LoginRequest;
import com.eyehospital.dto.request.RegisterRequest;
import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.AuthResponse;
import com.eyehospital.entity.AppUser;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.repository.UserRepository;
import com.eyehospital.security.JwtService;
import com.eyehospital.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BusinessException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String token = jwtService.generateToken(userPrincipal);

        return AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .token(token)
                .build();
    }

    public ApiResponse<String> register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }

        AppUser appUser = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .active(request.getActive() == null ? true : request.getActive())
                .build();

        userRepository.save(appUser);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User registered successfully")
                .data(appUser.getUsername())
                .build();
    }
}