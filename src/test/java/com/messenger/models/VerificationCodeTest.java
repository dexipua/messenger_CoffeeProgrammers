package com.messenger.models;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerificationCodeTest {

    @Test
    void testData() {
        VerificationCode verificationCode1 = Instancio.create(VerificationCode.class);

        VerificationCode verificationCode2 = new VerificationCode();
        verificationCode2.setId(verificationCode1.getId());
        verificationCode2.setCode(verificationCode1.getCode());
        verificationCode2.setEmail(verificationCode1.getEmail());
        verificationCode2.setExpiryDate(verificationCode1.getExpiryDate());

        assertEquals(verificationCode1, verificationCode2);
        assertEquals(verificationCode1.toString(), verificationCode2.toString());
        assertEquals(verificationCode1.hashCode(), verificationCode2.hashCode());
    }
}