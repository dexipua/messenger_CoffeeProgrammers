package com.messenger.models;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactTest {
    @Test
    void testData() {
        Contact contact1 = Instancio.create(Contact.class);

        Contact contact2 = new Contact();
        contact2.setId(contact1.getId());
        contact2.setAccountId(contact1.getAccountId());

        assertEquals(contact1, contact2);
        assertEquals(contact1.hashCode(), contact2.hashCode());
        assertEquals(contact1.toString(), contact2.toString());
    }
}