package com.messenger.services.interfaces;

import com.messenger.models.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    List<Account> getAll();
    Account findById(long id);
    List<Account> findAllContacts(long id);
    Account update(Account account, long id);
}