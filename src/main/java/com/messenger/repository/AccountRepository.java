package com.messenger.repository;

import com.messenger.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByEmail(String email);
    Page<Account> findAllByLastNameContainsAndFirstNameContains
            (String lastName, String firstName, PageRequest page);
    boolean existsByEmail(String email);
}