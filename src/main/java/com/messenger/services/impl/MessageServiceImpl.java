package com.messenger.services.impl;

import com.messenger.models.Message;
import com.messenger.repository.MessageRepository;
import com.messenger.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAllByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId,
                Sort.by(Sort.Direction.ASC, "date"));
    }

    @Override
    public Message create(Message message) {
        return messageRepository.save(message);
    }
}
