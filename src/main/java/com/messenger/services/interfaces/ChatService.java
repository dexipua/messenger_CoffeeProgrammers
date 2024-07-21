package com.messenger.services.interfaces;

import com.messenger.models.Chat;

import java.util.List;

public interface ChatService {
    Chat create(Long firstId, Long secondId);
    void delete(Long chatId);
    Chat findById(Long chatId);
    List<Chat> findByAccountId(Long accountId);
}
