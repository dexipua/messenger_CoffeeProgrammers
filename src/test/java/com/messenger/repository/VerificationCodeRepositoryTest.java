package com.messenger.repository;

import com.messenger.models.VerificationCode;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VerificationCodeRepositoryTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    private VerificationCode verificationCode;

    @BeforeEach
    void setUp() {
        verificationCode = Instancio.create(VerificationCode.class);
        verificationCodeRepository.save(verificationCode);
    }

    @AfterEach
    void tearDown() {
        verificationCodeRepository.deleteAll();
    }

    @Test
    void deleteAllByEmail() {
        verificationCodeRepository.deleteAllByEmail(verificationCode.getEmail());

        assertEquals(0, verificationCodeRepository.count());
    }

    @Test
    void existsByEmail() {
        assertTrue(verificationCodeRepository.existsByEmail(verificationCode.getEmail()));
        assertFalse(verificationCodeRepository.existsByEmail("sdsdsds"));
    }



    @Test
    void findByEmail() {
        assertEquals(verificationCode.getEmail(), verificationCodeRepository.findByEmail(verificationCode.getEmail()).get().getEmail());
        assertThrows(NoSuchElementException.class, () -> verificationCodeRepository.findByEmail("sdsdsds").get());
    }
}