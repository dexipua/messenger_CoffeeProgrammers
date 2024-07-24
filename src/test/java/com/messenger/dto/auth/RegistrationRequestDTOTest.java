package com.messenger.dto.auth;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationRequestDTOTest {

    @Test
    void testData() {
        RegistrationRequestDTO registrationRequestDTO1 = Instancio.create(RegistrationRequestDTO.class);

        RegistrationRequestDTO registrationRequestDTO2 = new RegistrationRequestDTO();
        registrationRequestDTO2.setUsername(registrationRequestDTO1.getUsername());
        registrationRequestDTO2.setPassword(registrationRequestDTO1.getPassword());
        registrationRequestDTO2.setDescription(registrationRequestDTO1.getDescription());
        registrationRequestDTO2.setFirstName(registrationRequestDTO1.getFirstName());
        registrationRequestDTO2.setLastName(registrationRequestDTO1.getLastName());
        registrationRequestDTO2.setRole(registrationRequestDTO1.getRole());

        assertEquals(registrationRequestDTO1, registrationRequestDTO2);
        assertEquals(registrationRequestDTO1.hashCode(), registrationRequestDTO2.hashCode());
        assertEquals(registrationRequestDTO1.toString(), registrationRequestDTO2.toString());
    }
}