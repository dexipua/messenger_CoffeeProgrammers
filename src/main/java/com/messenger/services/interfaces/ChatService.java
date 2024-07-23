package com.messenger.services.interfaces;

import com.messenger.models.Chat;

import java.util.List;

public interface ChatService {
    Chat create(Long firstId, Long secondId, String name);
    void delete(Long chatId);
    Chat findById(Long chatId);
    List<Chat> findByAccountId(Long accountId);
    List<Chat> findChatsByAccountIds(List<Long> accountsIds);
}
