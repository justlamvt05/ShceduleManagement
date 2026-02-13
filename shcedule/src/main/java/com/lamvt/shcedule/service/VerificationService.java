package com.lamvt.shcedule.service;

import com.lamvt.shcedule.entity.User;

public interface VerificationService {
    String createToken(User user);
    String validateToken(String token);
    void deleteToken(String token);
}