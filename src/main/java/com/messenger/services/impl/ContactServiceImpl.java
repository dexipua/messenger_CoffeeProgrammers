package com.messenger.services.impl;

import com.messenger.models.Contact;
import com.messenger.repository.ContactRepository;
import com.messenger.services.interfaces.ContactService;
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
}
