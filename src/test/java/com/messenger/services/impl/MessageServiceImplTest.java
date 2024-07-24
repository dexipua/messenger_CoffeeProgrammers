package com.messenger.services.impl;

import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import com.messenger.repository.MessageRepository;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ChatService chatService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private MessageServiceImpl messageService;

    private Message message;
    private Message messageToUpdate;
    private Chat chat;
    private Account account;
    private Account account2;
    @BeforeEach
    void setUp() {
        messageToUpdate = new Message();
        messageToUpdate.setText("newText");
        messageToUpdate.setId(1L);
        chat = new Chat();
        chat.setId(1L);
        account = new Account();
        account.setId(1L);
        account2 = new Account();
        account2.setId(2L);
        chat.setAccounts(List.of(account, account2));
        message = new Message();
        message.setId(1L);
        message.setSender(account);
        message.setDate(LocalDateTime.MAX);
        message.setText("sds123dsd");
        message.setChat(chat);
    }

    @Test
    void getAllByChatId() {
        when(messageRepository.findByChatId(chat.getId(), Sort.by(Sort.Direction.ASC, "date"))).thenReturn(List.of(message));
        assertEquals(List.of(message), messageService.getAllByChatId(chat.getId()));
        verify(messageRepository, times(1)).findByChatId(chat.getId(), Sort.by(Sort.Direction.ASC, "date"));
    }

    @Test
    void create() {
        when(messageRepository.save(message)).thenReturn(message);
        assertEquals(message, messageService.create(message));
        verify(messageRepository, times(1)).save(message);
    }
}