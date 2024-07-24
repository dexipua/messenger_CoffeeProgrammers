package com.messenger.mapper;

import com.messenger.dto.account.AccountRequest;
import com.messenger.dto.account.AccountResponse;
import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void toResponseSimple_ValidAccount() {
        // given
        Account account = new Account();
        account.setEmail("email@gmail.com");
        account.setId(1L);

        Chat chat = new Chat();
        chat.setId(1L);
        chat.setAccounts(Collections.singletonList(account));


        // when
        AccountResponseSimple accountResponse = accountMapper.toResponseSimple(account);

        // then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getId()).isEqualTo(1L);
        assertThat(accountResponse.getFirstName()).isEqualTo(account.getFirstName());
        assertThat(accountResponse.getEmail()).isEqualTo(account.getEmail());
    }

    @Test
    void toResponseSimple_accountIsNull() {
        // given
        Account account = null;

        // when
        AccountResponseSimple accountResponse = accountMapper.toResponseSimple(account);

        // then
        assertThat(accountResponse).isNull();
    }


    @Test
    void toResponse_ValidAccount() {
        // given
        Account account = new Account();
        account.setEmail("email@gmail.com");
        account.setId(1L);

        Chat chat = new Chat();
        chat.setId(1L);
        chat.setAccounts(Collections.singletonList(account));


        // when
        AccountResponse accountResponse = accountMapper.toResponse(account);

        // then
        assertThat(accountResponse).isNotNull();
        assertThat(accountResponse.getId()).isEqualTo(1L);
        assertThat(accountResponse.getDescription()).isEqualTo(account.getDescription());
        assertThat(accountResponse.getEmail()).isEqualTo(account.getEmail());
    }

    @Test
    void toResponse_AccountIsNull() {
        // given
        Account account = null;

        // when
        AccountResponse accountResponse = accountMapper.toResponse(account);

        // then
        assertThat(accountResponse).isNull();
    }

    @Test
    void toModel() {
        AccountRequest accountRequest = Instancio.create(AccountRequest.class);
        Account account = accountMapper.toModel(accountRequest);
        assertThat(account).isNotNull();
        assertThat(account.getDescription()).isEqualTo(accountRequest.getDescription());
    }
    @Test
    void toModelNull() {
        Account account = accountMapper.toModel(null);
        assertThat(account).isNull();
    }
}