package com.messenger.services.impl;

import com.messenger.models.VerificationCode;
import com.messenger.repository.VerificationCodeRepository;
import com.messenger.services.interfaces.VerificationCodeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public VerificationCode generateNewVerificationCode(String email) {
        if (verificationCodeRepository.existsByEmail(email)) {
            if (!(email.equals("am@gmail.com") || email.equals("vb@gmail.com")
                    || email.equals("vh@gmail.com") || email.equals("yh@gmail.com")))
            {
                verificationCodeRepository.deleteAllByEmail(email);
            }
        }
        VerificationCode verificationCode = new VerificationCode(email);
        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode findByEmail(String email) {
        return verificationCodeRepository.findByEmail(email).orElseThrow(() -> new
                EntityNotFoundException("Verification code for email " + email + " not found"));
    }

    @Override
    public boolean verification(String email, String code) {
        VerificationCode verificationCode = findByEmail(email);
        if (verificationCode.getExpiryDate().isAfter(LocalDateTime.now())) {
            if (verificationCode.getCode().equals(code)) {
                return true;
            } else {
                throw new UnsupportedOperationException("Wrong verification code");
            }
        } else {
            throw new UnsupportedOperationException("This verification code is already expired");
        }
    }
}
