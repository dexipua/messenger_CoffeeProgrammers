package com.messenger.services.impl;

import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import com.messenger.repository.MessageRepository;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        message.setAccount(account);
        message.setDate(LocalDateTime.MAX);
        message.setText("sds123dsd");
        message.setChat(chat);
    }

    @Test
    void getAllByChatId() {
        when(messageRepository.findByChatId(chat.getId())).thenReturn(List.of(message));
        assertEquals(List.of(message), messageService.getAllByChatId(chat.getId()));
        verify(messageRepository, times(1)).findByChatId(chat.getId());
    }

    @Test
    void getAllByChatIdAndTextContaining() {
       when(messageRepository.findByChatIdAndTextContaining(chat.getId(), message.getText().substring(1, message.getText().length() - 1))).thenReturn(List.of(message));
       assertEquals(List.of(message), messageService.getAllByChatIdAndTextContaining(chat.getId(), message.getText().substring(1, message.getText().length() - 1)));
       verify(messageRepository, times(1)).findByChatIdAndTextContaining(chat.getId(), message.getText().substring(1, message.getText().length() - 1));
    }

    @Test
    void create() {
        when(messageRepository.save(message)).thenReturn(message);
        when(chatService.findById(message.getChat().getId())).thenReturn(chat);
        when(accountService.findById(message.getAccount().getId())).thenReturn(account);
        assertEquals(message, messageService.create(message.getChat().getId(), message.getAccount().getId(), message));
        verify(messageRepository, times(1)).save(message);
        verify(chatService, times(1)).findById(message.getChat().getId());
        verify(accountService, times(1)).findById(message.getAccount().getId());
    }

    @Test
    void notCreateChat() {
        when(chatService.findById(message.getChat().getId())).thenThrow(new EntityNotFoundException("Chat with id " + message.getChat().getId() + " not found"));
        assertThrows(EntityNotFoundException.class, () -> messageService.create(message.getChat().getId(), message.getAccount().getId(), message));
        verify(messageRepository, never()).save(message);
        verify(chatService, times(1)).findById(message.getChat().getId());
        verify(accountService, never()).findById(message.getAccount().getId());
    }

    @Test
    void notCreateAccount() {
        when(accountService.findById(message.getAccount().getId())).thenThrow(new EntityNotFoundException("Account with id " + message.getAccount().getId() + " not found"));
        assertThrows(EntityNotFoundException.class, () -> messageService.create(message.getChat().getId(), message.getAccount().getId(), message));
        verify(messageRepository, never()).save(message);
    }

    @Test
    void update() {
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));
        when(messageRepository.save(message)).thenReturn(message);
        assertEquals(messageToUpdate.getText(), messageService.update(messageToUpdate).getText());
        verify(messageRepository, times(1)).findById(message.getId());
    }

    @Test
    void findById() {
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));
        assertEquals(message, messageService.findById(message.getId()));
        verify(messageRepository, times(1)).findById(message.getId());
    }

    @Test
    void notFoundById() {
        when(messageRepository.findById(message.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> messageService.findById(message.getId()));
        verify(messageRepository, times(1)).findById(message.getId());
    }
}