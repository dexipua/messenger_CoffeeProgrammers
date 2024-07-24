package com.messenger.services.impl;

import com.messenger.models.Contact;
import com.messenger.repository.ContactRepository;
import com.messenger.services.interfaces.ContactService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findById(long contactId) {
        return contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + contactId));
    }

    @Override
    public Contact findByAccountId(long accountId) {
        return contactRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with accountId: " + accountId));
    }
}
