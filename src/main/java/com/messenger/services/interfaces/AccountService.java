package com.messenger.services.interfaces;

import com.messenger.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    Object[] findAll(int page, int size);
    Account findById(long id);
    List<Account> findAllContacts(long id);
    Account update(Account account, long id);
    Object[] findByNames(String lastName, String firstName, int page, int size);
    boolean isExistByEmail(String email);
    Account addContact(long id, long contactId);
    Account removeContact(long id, long contactId);
    List<Account> findAccountsNotInContactList(Long accountId, int page, int size);
}