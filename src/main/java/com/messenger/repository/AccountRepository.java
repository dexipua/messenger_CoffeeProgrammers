package com.messenger.repository;

import com.messenger.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByEmail(String email);
    List<Account> findAllByLastNameContainsAndFirstNameContains
            (String lastName, String firstName);
    List<Account> findByEmailContains(String email);
}