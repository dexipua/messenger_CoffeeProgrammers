package com.messenger.services.impl;

import com.messenger.mapper.ChatMapper;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.repository.ChatRepository;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    public final ChatRepository chatRepository;
    public final ChatMapper chatMapper;
    public final AccountService accountService;


    @Override
    public Chat create(Long firstId, Long secondId, String name) {
        Chat chat = new Chat();
        chat.getAccounts().add(accountService.findById(firstId));
        chat.getAccounts().add(accountService.findById(secondId));
        chat.setName(name);
        return chatRepository.save(chat);
    }

    @Override
    public void delete(Long chatId) {
        findById(chatId);
        chatRepository.deleteById(chatId);
    }

    @Override
    public Chat findById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(
                () -> new EntityNotFoundException("Chat with id " + chatId + "not found"));

    }

    @Override
    public List<Chat> findByAccountId(Long accountId) {
        accountService.findById(accountId);
        return chatRepository.findAllByAccountId(accountId);
    }

    @Override
    public Long findChatsByAccountIds(List<Long> accountsIds){
        return chatRepository.findChatsByAccountIds(accountsIds);
    }
}
