package com.messenger.dto.account;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountListResponseDTOTest {

    @Test
    void testData() {
        AccountListResponseDTO accountListResponseDTO1 = Instancio.create(AccountListResponseDTO.class);

        AccountListResponseDTO accountListResponseDTO2 = new AccountListResponseDTO();
        accountListResponseDTO2.setList(accountListResponseDTO1.getList());
        accountListResponseDTO2.setPages(accountListResponseDTO1.getPages());

        assertEquals(accountListResponseDTO1, accountListResponseDTO2);
        assertEquals(accountListResponseDTO1.hashCode(), accountListResponseDTO2.hashCode());
        assertEquals(accountListResponseDTO1.toString(), accountListResponseDTO2.toString());
    }
}