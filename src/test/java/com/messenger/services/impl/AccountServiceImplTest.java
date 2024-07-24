package com.messenger.services.impl;

import com.messenger.models.Account;
import com.messenger.models.Contact;
import com.messenger.repository.AccountRepository;
import com.messenger.services.interfaces.ContactService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ContactService contactService;
    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = Instancio.of(Account.class)
                .create();
    }

    @Test
    void create() {
        // when
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.empty());
        when(contactService.create(any(Contact.class))).thenReturn(Instancio.create(Contact.class));
        // then
        accountService.create(account);
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(1)).save(argumentCaptor.capture());

        verify(accountRepository, times(1)).findByEmail(account.getEmail());

        Account captured = argumentCaptor.getValue();
        assertEquals(account, captured);
    }

    @Test
    void notCreate() {
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        assertThrows(EntityExistsException.class, () -> accountService.create(account));
        verify(accountRepository, times(1)).findByEmail(account.getEmail());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void loadUserByUsername() {
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        assertEquals(account, accountService.loadUserByUsername(account.getEmail()));
        verify(accountRepository, times(1)).findByEmail(account.getEmail());
    }

    @Test
    void notLoadUserByUsername() {
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.loadUserByUsername(account.getEmail()));
        verify(accountRepository, times(1)).findByEmail(account.getEmail());
    }

    @Test
    void getAll() {
        when(accountRepository.findAll()).thenReturn(List.of(account));
        assertEquals(List.of(account), accountService.findAll());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        assertEquals(account, accountService.findById(account.getId()));
        verify(accountRepository, times(1)).findById(account.getId());
    }

    @Test
    void notFindById() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.findById(account.getId()));
        verify(accountRepository, times(1)).findById(account.getId());
    }
}