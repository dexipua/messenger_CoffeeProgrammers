package com.messenger.dto.account;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountResponseTest {

    @Test
    void testData() {
        AccountResponse accountResponse1 = Instancio.create(AccountResponse.class);

        AccountResponse accountResponse2 = new AccountResponse();
        accountResponse2.setFirstName(accountResponse1.getFirstName());
        accountResponse2.setLastName(accountResponse1.getLastName());
        accountResponse2.setAccounts(accountResponse1.getAccounts());
        accountResponse2.setEmail(accountResponse1.getEmail());
        accountResponse2.setRole(accountResponse1.getRole());
        accountResponse2.setId(accountResponse1.getId());
        accountResponse2.setStatus(accountResponse1.getStatus());
        accountResponse2.setDescription(accountResponse1.getDescription());

        assertEquals(accountResponse1, accountResponse2);
        assertEquals(accountResponse1.hashCode(), accountResponse2.hashCode());
        assertEquals(accountResponse1.toString(), accountResponse2.toString());
    }
}