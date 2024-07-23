package com.messenger.services.impl;

import com.messenger.models.Message;
import com.messenger.repository.MessageRepository;
import com.messenger.services.interfaces.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAllByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    @Override
    public List<Message> getAllByChatIdAndTextContaining(Long chatId, String text) {
        return messageRepository.findByChatIdAndTextContaining(chatId, text);
    }

    @Override
    public Message create(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message update(Long chatId, Message message) {
        Message messageToUpdate = findById(message.getId());
        messageToUpdate.setText(message.getText());
        return messageRepository.save(messageToUpdate);
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with id " + id + " not found"));
    }
}
