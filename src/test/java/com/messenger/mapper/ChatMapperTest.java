package com.messenger.mapper;

import com.messenger.dto.chat.ChatResponse;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ChatMapperTest {

    private final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);

    @Test
    void toResponse_ValidChat() {
        // given
        Message message = new Message();
        message.setText("Hello");

        Account account = new Account();
        account.setEmail("email@gmail.com");

        Chat chat = new Chat();
        chat.setId(1L);
        chat.setAccounts(Collections.singletonList(account));

        // when
        ChatResponse chatResponse = chatMapper.toResponse(chat);

        // then
        assertThat(chatResponse).isNotNull();
        assertThat(chatResponse.getId()).isEqualTo(1L);
        assertThat(chatResponse.getAccounts().getFirst().getEmail()).isEqualTo("email@gmail.com");
    }

    @Test
    void toResponse_ChatIsNull() {
        // given
        Message message = new Message();
        message.setText("Hello");

        Account account = new Account();
        account.setEmail("email@gmail.com");

        Chat chat = null;

        // when
        ChatResponse chatResponse = chatMapper.toResponse(chat);

        // then
        assertThat(chatResponse).isNull();
    }
}