package com.messenger.mapper;

import com.messenger.dto.account.AccountRequest;
import com.messenger.dto.account.AccountResponse;
import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.models.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    Account toModel(AccountRequest accountRequest);
    AccountResponse toResponse(Account account);
    AccountResponseSimple toResponseSimple(Account account);
}
