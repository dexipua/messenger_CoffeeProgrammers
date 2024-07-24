package com.messenger.repository;

import com.messenger.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    void deleteAllByEmail(String email);
    boolean existsByEmail(String email);
    Optional<VerificationCode> findByEmail(String email);
}