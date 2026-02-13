package com.lamvt.shcedule.service.impl;

import com.lamvt.shcedule.constraint.AuthProvider;
import com.lamvt.shcedule.constraint.EStatus;
import com.lamvt.shcedule.dto.UserDto;
import com.lamvt.shcedule.entity.User;
import com.lamvt.shcedule.entity.VerificationToken;
import com.lamvt.shcedule.exception.EntityNotFound;
import com.lamvt.shcedule.exception.InvalidInputException;
import com.lamvt.shcedule.mapper.UserMapper;
import com.lamvt.shcedule.payload.request.LoginRequest;
import com.lamvt.shcedule.payload.request.RegisterRequest;
import com.lamvt.shcedule.payload.response.ApiCode;
import com.lamvt.shcedule.payload.response.ApiResponse;
import com.lamvt.shcedule.repository.UserRepository;
import com.lamvt.shcedule.repository.VerificationTokenRepository;
import com.lamvt.shcedule.service.AuthService;
import com.lamvt.shcedule.service.UserService;
import com.lamvt.shcedule.service.VerificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationService verificationService;
    private final UserService userService;


    @Override
    public UserDto login(LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest.getUsername());
        User user = userRepository.findByUsername(
                loginRequest.getUsername()).orElseThrow(()
                -> new EntityNotFound("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new EntityNotFound("Invalid username or password.");
        }

        return userMapper.toDto(user);
    }

    @Override
    public ApiResponse<?> register(RegisterRequest request) throws MessagingException {
        validateRegisterRequest(request);
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .authProvider(AuthProvider.GOOGLE)
                .status(EStatus.INACTIVE)
                .build();
        String token =  verificationService.createToken(user);
        userRepository.save(user);

        userService.sendVerificationEmail(user,token);
        return ApiResponse.success("Register successfully");
    }

    @Override
    public ApiResponse<?> confirm(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ApiResponse.error(ApiCode.CONFLICT,"Token is invalid or expired");
        }
        User user = verificationToken.getUser();
        user.setStatus(EStatus.ACTIVE);
        userRepository.save(user);

        return ApiResponse.success("Your account is activated");
    }
    private void validateRegisterRequest(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InvalidInputException("Password and Confirm Password do not match");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new InvalidInputException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidInputException("Email already exists");
        }

        if (userRepository.existsByPhone((request.getPhone()))) {
            throw new InvalidInputException("Phone number already exists");
        }
    }


}
