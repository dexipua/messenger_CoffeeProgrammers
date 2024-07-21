package com.messenger.services.interfaces;

import com.messenger.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllByChatId(Long chatId);
    List<Message> getAllByChatIdAndTextContaining(Long chatId, String text);
    Message create(Long chatId, Long senderId, Message message);
    Message update(Long chatId, Message message);
    Message findById(Long id);
}
