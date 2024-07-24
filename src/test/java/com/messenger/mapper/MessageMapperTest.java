package com.messenger.mapper;

import com.messenger.dto.message.MessageRequest;
import com.messenger.dto.message.MessageResponse;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

    @InjectMocks
    private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    @Mock
    private AccountService accountService;

    @Mock
    private ChatService chatService;

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

    @Test
    void toModel() {
        MessageRequest messageRequest = Instancio.create(MessageRequest.class);
        when(accountService.findById(messageRequest.getSenderId())).thenReturn(Instancio.create(Account.class));
        when(chatService.findById(messageRequest.getChatId())).thenReturn(Instancio.create(Chat.class));
        Message message = messageMapper.toModel(messageRequest);
        assertThat(message).isNotNull();
        assertThat(message.getText()).isEqualTo(messageRequest.getText());
    }
    @Test
    void toModelNull() {
        Message message = messageMapper.toModel(null);
        assertThat(message).isNull();
    }
}