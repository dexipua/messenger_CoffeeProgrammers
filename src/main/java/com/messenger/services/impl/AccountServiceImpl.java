package com.messenger.services.impl;

import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.mapper.AccountMapper;
import com.messenger.models.Account;
import com.messenger.repository.AccountRepository;
import com.messenger.services.interfaces.AccountService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public Account create(Account account) {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new EntityExistsException("User with email " + account.getEmail() + " already exists");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Account with email " + username + " not found"));
    }

    @Override
    public List<AccountResponseSimple> getAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toResponseSimple).toList();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account with id " + id + " not found")
        );
    }
}
