package com.messenger.dto.account;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRequestTest {

    @Test
    void testData() {
        AccountRequest accountRequest1 = Instancio.create(AccountRequest.class);

        AccountRequest accountRequest2 = new AccountRequest();
        accountRequest2.setFirstName(accountRequest1.getFirstName());
        accountRequest2.setLastName(accountRequest1.getLastName());
        accountRequest2.setDescription(accountRequest1.getDescription());

        assertEquals(accountRequest1, accountRequest2);
        assertEquals(accountRequest1.hashCode(), accountRequest2.hashCode());
        assertEquals(accountRequest1.toString(), accountRequest2.toString());
    }
}