package com.messenger.dto.chat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatRequestTest {

    @Test
    void testData() {
        ChatRequest chatRequest1 = Instancio.create(ChatRequest.class);

        ChatRequest chatRequest2 = new ChatRequest();
        chatRequest2.setName(chatRequest1.getName());

        assertEquals(chatRequest1, chatRequest2);
        assertEquals(chatRequest1.toString(), chatRequest2.toString());
        assertEquals(chatRequest1.hashCode(), chatRequest2.hashCode());
    }
}