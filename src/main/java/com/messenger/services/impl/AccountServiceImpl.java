package com.messenger.services.impl;

import com.messenger.models.Account;
import com.messenger.models.Contact;
import com.messenger.repository.AccountRepository;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ContactService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final ContactService contactService;


    @Override
    public Account create(Account account) {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new EntityExistsException("User with email " +
                    account.getEmail() + " already exists");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account created = accountRepository.save(account);
        contactService.create(new Contact(created.getId()));
        return created;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Account with email " + username + " not found"));
    }

    @Override
    public List<Account> findAllContacts(long id){
        return findById(id).getContacts().stream().map(
                a -> findById(a.getId())).toList();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account with id " + id + " not found")
        );
    }

    @Override
    public Account update(Account account, long id) {
        Account old = findById(id);
        old.setDescription(account.getDescription());
        old.setFirstName(account.getFirstName());
        old.setLastName(account.getLastName());
        return accountRepository.save(old);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account addContact(long id, long contactId){
        Account account = findById(id);

        account.getContacts().add(contactService.findById(contactId));
        Account toAdd = findById(contactService.findById(contactId).getAccountId());
        return accountRepository.save(toAdd);}

    @Override
    public Account removeContact(long id, long contactId){
        Account account = findById(id);

        account.getContacts().remove(contactService.findById(contactId));

        return accountRepository.save(account);
    }

    @Override
    public Page<Account> findAccountsNotInContactList(Long accountId, int page, int size) {
        return accountRepository.findAccountsNotInContactList(accountId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "lastName", "firstName")));
    }

    @Override
    public Page<Account> findAccountsNotInContactListAndSearchByNamesIgnoreCase(
            Long accountId, int page, int size, String firstName, String lastName) {
        return accountRepository.findByFirstNameAndLastNameExcludingId(
                firstName, lastName, accountId, PageRequest.of(page, size,
                        Sort.by(Sort.Direction.ASC, "lastName", "firstName")));
    }
}
