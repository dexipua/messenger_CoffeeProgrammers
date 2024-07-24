package com.messenger.repository;

import com.messenger.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT a FROM Chat a JOIN a.accounts acc WHERE acc.id =:id")
    List<Chat> findAllByAccountId(@Param("id") Long id);
}
