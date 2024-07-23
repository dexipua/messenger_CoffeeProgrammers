package com.messenger.repository;

import com.messenger.models.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findByChatId(Long chatId, Sort sort);
    List<Message> findByChatIdAndTextContaining(Long chatId, String text, Sort sort);
}