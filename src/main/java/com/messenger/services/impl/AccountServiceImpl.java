package com.messenger.services.impl;

import com.messenger.models.Account;
import com.messenger.models.Contact;
import com.messenger.repository.AccountRepository;
import com.messenger.repository.ContactRepository;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ContactService;
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
    private final ContactService contactService;
    private final ContactRepository contactRepository;


    @Override
    public Account create(Account account) {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new EntityExistsException("User with email " +
                    account.getEmail() + " already exists");
        }
        contactService.create(new Contact(account.getId()));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Account with email " + username + " not found"));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account with id " + id + " not found")
        );
    }

    @Override
    public List<Account> findAllContacts(long id){
        return findById(id).getContacts().stream().map(
                a -> findById(a.getId())
        ).toList();
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
    public List<Account> findByNames(String lastName, String firstName) {
        return accountRepository.findAllByLastNameContainsAndFirstNameContains(lastName, firstName);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account addContact(long id, long contactId){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + id));

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with id: " + id));;

        account.getContacts().add(contact);
        return accountRepository.save(account);
    }

    @Override
    public Account removeContact(long id, long contactId){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + id));

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with id: " + id));;

        account.getContacts().remove(contact);
        return accountRepository.save(account);
    }
}
