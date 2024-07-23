package com.messenger.services.interfaces;

import com.messenger.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllByChatId(Long chatId);
    Message create(Message message);
}
