package com.lamvt.shcedule.service.impl;

import com.lamvt.shcedule.constraint.EStatus;
import com.lamvt.shcedule.entity.User;
import com.lamvt.shcedule.entity.VerificationToken;
import com.lamvt.shcedule.repository.UserRepository;
import com.lamvt.shcedule.repository.VerificationTokenRepository;
import com.lamvt.shcedule.service.VerificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {


    private final VerificationTokenRepository tokenRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        tokenRepository.save(verificationToken);
        
        return token;
    }

    @Override
    @Transactional
    public String validateToken(String token) {
        VerificationToken vToken = tokenRepository.findByToken(token);

        if (vToken == null) {
            return "INVALID";
        }

        if (vToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(vToken);
            return "EXPIRED";
        }


        User user = vToken.getUser();
        user.setStatus(EStatus.ACTIVE);
        userRepository.save(user);
        
        tokenRepository.delete(vToken);
        
        return "SUCCESS";
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}