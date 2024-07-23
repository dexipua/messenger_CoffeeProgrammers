package com.messenger.repository;

import com.messenger.dto.chat.ChatResponse;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT a FROM Chat a JOIN a.accounts acc WHERE acc.id =:id")
    List<Chat> findAllByAccountId(Long id);

    @Query("SELECT c FROM Chat c JOIN c.accounts a WHERE a.id IN :accountIds")
    Long findChatsByAccountIds(@Param("accountIds") List<Long> accountIds);
}
