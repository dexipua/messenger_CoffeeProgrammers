package com.messenger.dto.message;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageRequestTest {
    @Test
    void testData() {
        MessageRequest messageRequest1 = Instancio.create(MessageRequest.class);

        MessageRequest messageRequest2 = new MessageRequest();
        messageRequest2.setText(messageRequest1.getText());
        messageRequest2.setChatId(messageRequest1.getChatId());
        messageRequest2.setSenderId(messageRequest1.getSenderId());

        assertEquals(messageRequest1, messageRequest2);
        assertEquals(messageRequest1.hashCode(), messageRequest2.hashCode());
        assertEquals(messageRequest1.toString(), messageRequest2.toString());
    }
}