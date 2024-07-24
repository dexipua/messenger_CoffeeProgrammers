package com.messenger.services.impl;

import com.messenger.models.VerificationCode;
import com.messenger.repository.VerificationCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceImplTest {

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @InjectMocks
    private VerificationCodeServiceImpl verificationCodeService;

    @Test
    void generateNewVerificationCodeExists() {
        VerificationCode verificationCode = new VerificationCode("email@gmail.com");

        when(verificationCodeRepository.existsByEmail(verificationCode.getEmail())).thenReturn(true);
        when(verificationCodeRepository.findByEmail(verificationCode.getEmail())).thenReturn(Optional.of(verificationCode));
        when(verificationCodeRepository.save(any(VerificationCode.class))).thenReturn(new VerificationCode(verificationCode.getEmail()));

        VerificationCode result = verificationCodeService.generateNewVerificationCode(verificationCode.getEmail());

        verify(verificationCodeRepository, times(1)).existsByEmail(verificationCode.getEmail());
        verify(verificationCodeRepository, times(1)).findByEmail(verificationCode.getEmail());
        verify(verificationCodeRepository, times(1)).save(any(VerificationCode.class));

        assertNotNull(result);
        assertEquals(verificationCode.getEmail(), result.getEmail());
    }
    @Test
    void generateNewVerificationCodeNotExists() {
        String email = "email@gmail.com";

        when(verificationCodeRepository.existsByEmail(email)).thenReturn(false);
        when(verificationCodeRepository.save(any(VerificationCode.class))).thenReturn(new VerificationCode(email));

        VerificationCode result = verificationCodeService.generateNewVerificationCode(email);

        verify(verificationCodeRepository, times(1)).existsByEmail(email);
        verify(verificationCodeRepository, never()).findByEmail(email);
        verify(verificationCodeRepository, times(1)).save(any(VerificationCode.class));

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void findByEmail() {
        VerificationCode verificationCode = new VerificationCode("email@gmail.com");
        when(verificationCodeRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(verificationCode));
        assertEquals(verificationCode.getCode(), verificationCodeService.findByEmail("email@gmail.com").getCode());
        verify(verificationCodeRepository, times(1)).findByEmail("email@gmail.com");
    }

    @Test
    void notFindByEmail() {
        when(verificationCodeRepository.findByEmail("email@gmail.com")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> verificationCodeService.findByEmail("email@gmail.com"));
        verify(verificationCodeRepository, times(1)).findByEmail("email@gmail.com");
    }

    @Test
    void verification() {
        String email = "email@gmail.com";
        VerificationCode verificationCode = new VerificationCode(email);
        when(verificationCodeRepository.findByEmail(email)).thenReturn(Optional.of(verificationCode));
        assertTrue(verificationCodeService.verification(email, verificationCode.getCode()));
        verify(verificationCodeRepository, times(1)).findByEmail(email);
    }

    @Test
    void verificationExpired() {
        String email = "email@gmail.com";
        VerificationCode verificationCode = new VerificationCode(email);
        verificationCode.setExpiryDate(LocalDateTime.now().minusMinutes(2));
        when(verificationCodeRepository.findByEmail(email)).thenReturn(Optional.of(verificationCode));
        assertThrows(UnsupportedOperationException.class, () -> verificationCodeService.verification(email, verificationCode.getCode()));
        verify(verificationCodeRepository, times(1)).findByEmail(email);
    }

    @Test
    void verificationWrong() {
        String email = "email@gmail.com";
        VerificationCode verificationCode = new VerificationCode(email);
        when(verificationCodeRepository.findByEmail(email)).thenReturn(Optional.of(verificationCode));
        assertThrows(UnsupportedOperationException.class, () -> verificationCodeService.verification(email, "-"));
        verify(verificationCodeRepository, times(1)).findByEmail(email);
    }

    @Test
    void existsByEmail() {
        String email = "email@gmail.com";
        when(verificationCodeRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(verificationCodeService.existsByEmail(email));
        verify(verificationCodeRepository, times(1)).existsByEmail(email);
    }

    @Test
    void notExistsByEmail() {
        String email = "email@gmail.com";
        when(verificationCodeRepository.existsByEmail(email)).thenReturn(false);
        assertFalse(verificationCodeService.existsByEmail(email));
        verify(verificationCodeRepository, times(1)).existsByEmail(email);
    }
}