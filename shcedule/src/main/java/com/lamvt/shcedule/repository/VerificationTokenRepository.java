package com.lamvt.shcedule.repository;

import com.lamvt.shcedule.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    VerificationToken findByToken(String token);

    void deleteByToken(String token);
}
