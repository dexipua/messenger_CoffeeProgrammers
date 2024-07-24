package com.messenger.dto.message;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageResponseTest {
    @Test
    void testData() {
        MessageResponse messageResponse1 = Instancio.create(MessageResponse.class);

        MessageResponse messageResponse2 = new MessageResponse();
        messageResponse2.setAccountResponse(messageResponse1.getAccountResponse());
        messageResponse2.setId(messageResponse1.getId());
        messageResponse2.setDate(messageResponse1.getDate());
        messageResponse2.setText(messageResponse1.getText());

        assertEquals(messageResponse1, messageResponse2);
        assertEquals(messageResponse1.hashCode(), messageResponse2.hashCode());
        assertEquals(messageResponse1.toString(), messageResponse2.toString());
    }
}