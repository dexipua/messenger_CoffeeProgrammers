package com.messenger.services.impl;

import com.messenger.models.Contact;
import com.messenger.repository.ContactRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = Instancio.create(Contact.class);
    }

    @Test
    void create() {
        when(contactRepository.save(contact)).thenReturn(contact);
        assertEquals(contact, contactService.create(contact));
        verify(contactRepository).save(contact);
    }
}