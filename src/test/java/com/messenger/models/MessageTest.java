package com.messenger.models;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    @Test
    void testData() {
        Message message1 = Instancio.create(Message.class);

        Message message2 = new Message();
        message2.setText(message1.getText());
        message2.setId(message1.getId());
        message2.setChat(message1.getChat());
        message2.setDate(message1.getDate());
        message2.setSender(message1.getSender());

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
        assertEquals(message1.toString(), message2.toString());
    }
}
