package com.messenger.dto.exception;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionResponseTest {

    @Test
    void testData() {
        ExceptionResponse exceptionResponse1 = Instancio.create(ExceptionResponse.class);

        ExceptionResponse exceptionResponse2 = new ExceptionResponse(exceptionResponse1.getMessages());

        assertEquals(exceptionResponse1, exceptionResponse2);
        assertEquals(exceptionResponse1.hashCode(), exceptionResponse2.hashCode());
        assertEquals(exceptionResponse1.toString(), exceptionResponse2.toString());
    }
}