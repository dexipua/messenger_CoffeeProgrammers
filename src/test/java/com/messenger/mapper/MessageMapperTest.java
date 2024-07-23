package com.messenger.mapper;

import com.messenger.dto.message.MessageResponse;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class MessageMapperTest {

    private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    @Test
    void toResponse_ValidMessage() {
        // given
        Message message = new Message();
        message.setId(1L);
        message.setText("Hello");

        Account account = new Account();
        account.setEmail("email@gmail.com");
        account.setId(1L);

        Chat chat = new Chat();
        chat.setId(1L);
        chat.setAccounts(Collections.singletonList(account));

        message.setChat(chat);
        message.setSender(account);

        // when
        MessageResponse messageResponse = messageMapper.toResponse(message);

        // then
        assertThat(messageResponse).isNotNull();
        assertThat(messageResponse.getId()).isEqualTo(1L);
        assertThat(messageResponse.getText()).isEqualTo("Hello");
        assertThat(messageResponse.getSender()).isNotNull();
        assertThat(messageResponse.getSender().getId()).isEqualTo(1L);
    }

    @Test
    void toResponse_MessageIsNull() {
        // given
        Message message = null;

        // when
        MessageResponse messageResponse = messageMapper.toResponse(message);

        // then
        assertThat(messageResponse).isNull();
    }
}