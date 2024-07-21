package com.messenger.models;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    @Test
    void testData() {
        Account account1 = Instancio.create(Account.class);

        Account account2 = new Account();
        account2.setEmail(account1.getEmail());
        account2.setId(account1.getId());
        account2.setRole(account1.getRole());
        account2.setFirstName(account1.getFirstName());
        account2.setLastName(account1.getLastName());
        account2.setPassword(account1.getPassword());
        account2.setChats(account1.getChats());
        account2.setStatus(account1.getStatus());
        account2.setAccounts(account1.getAccounts());
        account2.setDescription(account1.getDescription());

        assertEquals(account1, account2);
        assertEquals(account1.hashCode(), account2.hashCode());
        assertEquals(account1.toString(), account2.toString());
    }
}