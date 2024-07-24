package com.messenger.dto.account;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountResponseSimpleTest {

    @Test
    void testData() {
        AccountResponseSimple accountResponseSimple1 = Instancio.create(AccountResponseSimple.class);

        AccountResponseSimple accountResponseSimple2 = new AccountResponseSimple();
        accountResponseSimple2.setFirstName(accountResponseSimple1.getFirstName());
        accountResponseSimple2.setLastName(accountResponseSimple1.getLastName());
        accountResponseSimple2.setEmail(accountResponseSimple1.getEmail());
        accountResponseSimple2.setId(accountResponseSimple1.getId());

        assertEquals(accountResponseSimple1, accountResponseSimple2);
        assertEquals(accountResponseSimple1.hashCode(), accountResponseSimple2.hashCode());
        assertEquals(accountResponseSimple1.toString(), accountResponseSimple2.toString());
    }
}