package com.messenger.services.interfaces;

import com.messenger.models.Contact;

public interface ContactService {
    Contact create(Contact contact);
    Contact findByAccountId(long accountId);
}
