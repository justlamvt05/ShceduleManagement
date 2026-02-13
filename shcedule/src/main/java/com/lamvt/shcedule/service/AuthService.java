package com.lamvt.shcedule.service;

import com.lamvt.shcedule.dto.UserDto;
import com.lamvt.shcedule.payload.request.LoginRequest;
import com.lamvt.shcedule.payload.request.RegisterRequest;
import com.lamvt.shcedule.payload.response.ApiResponse;
import jakarta.mail.MessagingException;


public interface AuthService {
    UserDto login(LoginRequest loginRequest);
    ApiResponse<?> register(RegisterRequest request) throws MessagingException;

    ApiResponse<?> confirm(String token);
}
