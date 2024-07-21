package com.messenger.services.interfaces;

import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.models.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    List<AccountResponseSimple> getAll();
    Account findById(long id);
}
