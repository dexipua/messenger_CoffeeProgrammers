package com.messenger.services.impl;

import com.messenger.mapper.ChatMapper;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.repository.ChatRepository;
import com.messenger.services.interfaces.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private ChatMapper chatMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private ChatServiceImpl chatService;

    private Chat chat;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        account1 = new Account();
        account1.setId(1L);
        account2 = new Account();
        account2.setId(2L);
        chat = new Chat();
        chat.setId(1L);
        chat.setAccounts(Arrays.asList(account1, account2));
    }

    @Test
    void create() {
        when(accountService.findById(1L)).thenReturn(account1);
        when(accountService.findById(2L)).thenReturn(account2);
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        Chat createdChat = chatService.create(1L, 2L);

        assertThat(createdChat.getAccounts()).containsExactlyInAnyOrder(account1, account2);
        verify(chatRepository, times(1)).save(any(Chat.class));
        verify(accountService, times(1)).findById(1L);
        verify(accountService, times(1)).findById(2L);
    }

    @Test
    void delete() {
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        chatService.delete(1L);

        verify(chatRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> chatService.delete(1L));
        verify(chatRepository, never()).deleteById(anyLong());
    }

    @Test
    void findById() {
        when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));

        Chat foundChat = chatService.findById(1L);

        assertThat(foundChat).isEqualTo(chat);
        verify(chatRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound() {
        when(chatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> chatService.findById(1L));
    }

    @Test
    void findByAccountId() {
        when(accountService.findById(1L)).thenReturn(account1);
        when(chatRepository.findAllByAccountId(1L)).thenReturn(Arrays.asList(chat));

        List<Chat> chats = chatService.findByAccountId(1L);

        assertThat(chats).containsExactly(chat);
        verify(accountService, times(1)).findById(1L);
        verify(chatRepository, times(1)).findAllByAccountId(1L);
    }
}
