package com.messenger.services.interfaces;

import com.messenger.models.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    Account findById(long id);
    Account update(Account account, long id);
    boolean isExistByEmail(String email);
    List<Account> findAllContacts(long id);
    Account addContact(long id, long contactId);
    Account removeContact(long id, long contactId);
    Page<Account> findAccountsNotInContactList(Long accountId, int page, int size);
    Page<Account> findAccountsNotInContactListAndSearchByNamesIgnoreCase(
            Long accountId, int page, int size, String firstName, String lastName);
}