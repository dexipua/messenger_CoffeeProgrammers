package com.messenger.repository;

import com.messenger.models.Account;
import com.messenger.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByAccountId(long accountId);
}
