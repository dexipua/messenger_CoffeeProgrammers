package com.messenger.repository;

import com.messenger.models.Account;
import com.messenger.models.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        //given
        Account account = new Account();
        account.setEmail("email@gmil.com");
        account.setPassword("passWord1");
        account.setDescription("Some");
        account.setFirstName("First");
        account.setLastName("Last");
        account.setRole(Role.USER);
        accountRepository.save(account);

        //when
        Account res = accountRepository.findByEmail("email@gmil.com").get();
        boolean result = res.equals(account);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void notFindByEmail() {
        //given
        Account account = new Account();
        account.setEmail("email@gmil.com");
        account.setPassword("passWord1");
        account.setDescription("Some");
        account.setFirstName("First");
        account.setLastName("Last");
        account.setRole(Role.USER);
        accountRepository.save(account);

        //then
        Optional<Account> res = accountRepository.findByEmail("efsevesf");
        assertThrows(NoSuchElementException.class, res::get);
    }
}