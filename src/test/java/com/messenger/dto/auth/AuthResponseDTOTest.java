package com.messenger.dto.auth;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthResponseDTOTest {

    @Test
    void testData() {
        AuthResponseDTO authResponseDTO1 = Instancio.create(AuthResponseDTO.class);

        AuthResponseDTO authResponseDTO2 = new AuthResponseDTO(authResponseDTO1.getUserId(), authResponseDTO1.getLastName(), authResponseDTO1.getFirstName(), authResponseDTO1.getEmail(), authResponseDTO1.getToken(), authResponseDTO1.getRole());

        assertEquals(authResponseDTO1, authResponseDTO2);
        assertEquals(authResponseDTO1.hashCode(), authResponseDTO2.hashCode());
        assertEquals(authResponseDTO1.toString(), authResponseDTO2.toString());
    }
}