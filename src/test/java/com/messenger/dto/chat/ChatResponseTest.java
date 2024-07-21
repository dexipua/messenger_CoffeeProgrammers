package com.messenger.dto.chat;

import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChatResponseTest {

    @Test
    void testGettersAndSetters() {
        // given
        Long id = 1L;
        List<Message> messages = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();

        // when
        ChatResponse chatResponse = new ChatResponse(id, messages, accounts);
        chatResponse.setId(2L);
        chatResponse.setMessages(Arrays.asList(new Message()));
        chatResponse.setAccounts(Arrays.asList(new Account()));

        // then
        assertThat(chatResponse.getId()).isEqualTo(2L);
        assertThat(chatResponse.getMessages()).hasSize(1);
        assertThat(chatResponse.getAccounts()).hasSize(1);
    }

    @Test
    void testEquals() { //todo 100%
        // given
        List<Message> messages1 = Arrays.asList(new Message());
        List<Account> accounts1 = Arrays.asList(new Account());

        List<Message> messages2 = Arrays.asList(new Message());
        List<Account> accounts2 = Arrays.asList(new Account());

        ChatResponse chatResponse1 = new ChatResponse(1L, messages1, accounts1);
        ChatResponse chatResponse2 = new ChatResponse(1L, messages1, accounts1);
        ChatResponse chatResponse3 = new ChatResponse(2L, messages2, accounts2);

        // then
        assertThat(chatResponse1).isEqualTo(chatResponse2);
        assertThat(chatResponse1).isNotEqualTo(chatResponse3);
        assertNotEquals(chatResponse1, null);
        assertNotEquals(chatResponse1, new Object());
    }

    @Test
    void testHashCode() { // todo 100%
        // given
        ChatResponse chatResponse1 = new ChatResponse(1L, Arrays.asList(new Message()), Arrays.asList(new Account()));
        ChatResponse chatResponse2 = new ChatResponse(1L, Arrays.asList(new Message()), Arrays.asList(new Account()));

        ChatResponse chatResponse3 = new ChatResponse(2L, null, null);

        // then
        assertThat(chatResponse1.hashCode()).isEqualTo(chatResponse2.hashCode());
        assertThat(chatResponse1.hashCode()).isNotEqualTo(chatResponse3.hashCode());
    }


    @Test
    void testToString() {
        // given
        ChatResponse chatResponse = new ChatResponse(1L, new ArrayList<>(), new ArrayList<>());

        // when
        String result = chatResponse.toString();

        // then
        assertThat(result).contains("ChatResponse");
        assertThat(result).contains("id=1");
    }
}
