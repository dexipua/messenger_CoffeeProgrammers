package com.messenger.dto.chat;

import com.messenger.dto.account.AccountResponseSimple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ChatResponseTest {

    @Test
    void testGettersAndSetters() {
        // when
        ChatResponse chatResponse = new ChatResponse(0L, "dfafsfdfsfds", new ArrayList<>());
        chatResponse.setId(2L);
        chatResponse.setAccounts(List.of(new AccountResponseSimple()));

        // then
        assertThat(chatResponse.getId()).isEqualTo(2L);
        assertThat(chatResponse.getAccounts()).hasSize(1);
    }

    @Test
    void testEquals() {
        // given
        List<AccountResponseSimple> accounts1 = List.of(new AccountResponseSimple());

        List<AccountResponseSimple> accounts2 = List.of(new AccountResponseSimple());

        ChatResponse chatResponse1 = new ChatResponse(1L, "fdgffs",accounts1);
        ChatResponse chatResponse2 = new ChatResponse(1L, "fdgffs", accounts1);
        ChatResponse chatResponse3 = new ChatResponse(2L, "fdgffs", accounts2);

        // then
        assertThat(chatResponse1).isEqualTo(chatResponse2);
        assertThat(chatResponse1).isNotEqualTo(chatResponse3);
        assertNotEquals(chatResponse1, null);
        assertNotEquals(chatResponse1, new Object());
    }

    @Test
    void testHashCode() {
        // given
        ChatResponse chatResponse1 = new ChatResponse(1L, "fdgffs", List.of(new AccountResponseSimple()));
        ChatResponse chatResponse2 = new ChatResponse(1L, "fdgffs",List.of(new AccountResponseSimple()));

        ChatResponse chatResponse3 = new ChatResponse(2L, "fdgffs", null);

        // then
        assertThat(chatResponse1.hashCode()).isEqualTo(chatResponse2.hashCode());
        assertThat(chatResponse1.hashCode()).isNotEqualTo(chatResponse3.hashCode());
    }


    @Test
    void testToString() {
        // given
        ChatResponse chatResponse = new ChatResponse(1L, "sggfs", new ArrayList<>());

        // when
        String result = chatResponse.toString();

        // then
        assertThat(result).contains("ChatResponse");
        assertThat(result).contains("id=1");
    }
}
