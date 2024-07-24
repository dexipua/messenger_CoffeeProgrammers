package com.messenger.services.impl;

import com.messenger.models.Contact;
import com.messenger.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void findById() {
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.findById(contact.getId()));
        verify(contactRepository, times(1)).findById(contact.getId());
    }

    @Test
    void notFindById() {
        when(contactRepository.findById(contact.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.findById(contact.getId()));
        verify(contactRepository, times(1)).findById(contact.getId());
    }

    @Test
    void findByAccountId() {
        when(contactRepository.findByAccountId(contact.getAccountId())).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.findByAccountId(contact.getAccountId()));
        verify(contactRepository, times(1)).findByAccountId(contact.getAccountId());
    }

    @Test
    void notFindByAccountId() {
        when(contactRepository.findByAccountId(contact.getAccountId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.findByAccountId(contact.getAccountId()));
        verify(contactRepository, times(1)).findByAccountId(contact.getAccountId());
    }
}