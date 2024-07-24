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
    boolean existsByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.id != :accountId AND a.id NOT IN " +
            "(SELECT ac.id FROM Account acc JOIN acc.contacts ac WHERE acc.id = :accountId)")
    Page<Account> findAccountsNotInContactList(@Param("accountId") Long accountId, PageRequest pageRequest);

    @Query("SELECT a FROM Account a WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) " +        "AND LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')) " +
            "AND a.id <> :excludedId")
    Page<Account> findByFirstNameAndLastNameExcludingId(@Param("firstName") String firstName,
                                                        @Param("lastName") String lastName,
                                                        @Param("excludedId") Long excludedId,
                                                        PageRequest pageRequest);

}