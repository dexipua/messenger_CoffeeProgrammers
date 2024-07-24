package com.messenger.dto.auth;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginRequestDTOTest {

    @Test
    void testData() {
        LoginRequestDTO loginRequestDTO1 = Instancio.create(LoginRequestDTO.class);

        LoginRequestDTO loginRequestDTO2 = new LoginRequestDTO();
        loginRequestDTO2.setUsername(loginRequestDTO1.getUsername());
        loginRequestDTO2.setPassword(loginRequestDTO1.getPassword());
        loginRequestDTO2.setCode(loginRequestDTO1.getCode());

        assertEquals(loginRequestDTO1, loginRequestDTO2);
        assertEquals(loginRequestDTO1.hashCode(), loginRequestDTO2.hashCode());
        assertEquals(loginRequestDTO1.toString(), loginRequestDTO2.toString());
    }
}