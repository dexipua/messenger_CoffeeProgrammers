package com.messenger.repository;

import com.messenger.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByEmail(String email);
    Page<Account> findAllByLastNameContainsAndFirstNameContains
            (String lastName, String firstName, PageRequest page);
    boolean existsByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.id != :accountId AND a.id NOT IN " +
            "(SELECT ac.id FROM Account acc JOIN acc.contacts ac WHERE acc.id = :accountId)")
    Page<Account> findAccountsNotInContactList(@Param("accountId") Long accountId, PageRequest pageRequest);

    @Query("SELECT a FROM Account a WHERE a.id <> :excludedId AND a.firstName LIKE %:firstName% AND a.lastName LIKE %:lastName%")
    Page<Account> findByFirstNameAndLastNameExcludingId(@Param("firstName") String firstName,
                                                        @Param("lastName") String lastName,
                                                        @Param("excludedId") Long excludedId,
                                                        PageRequest pageRequest);

}