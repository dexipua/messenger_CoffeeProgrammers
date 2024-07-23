package com.messenger.services.interfaces;

import com.messenger.models.VerificationCode;

public interface VerificationCodeService {
    VerificationCode generateNewVerificationCode(String email);
    VerificationCode findByEmail(String email);
    boolean verification(String email, String code);
    boolean existsByEmail(String email);
}
