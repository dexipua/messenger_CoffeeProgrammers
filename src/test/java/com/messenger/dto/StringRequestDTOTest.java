package com.messenger.dto;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringRequestDTOTest {

    @Test
    void testData() {
        StringRequestDTO stringRequestDTO1 = Instancio.create(StringRequestDTO.class);

        StringRequestDTO stringRequestDTO2 = new StringRequestDTO();
        stringRequestDTO2.setMessage(stringRequestDTO1.getMessage());

        assertEquals(stringRequestDTO1, stringRequestDTO2);
        assertEquals(stringRequestDTO1.toString(), stringRequestDTO2.toString());
        assertEquals(stringRequestDTO1.hashCode(), stringRequestDTO2.hashCode());
    }
}