package com.messenger.repository;

import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Chat chat;
    private Chat chat2;
    private Message message;
    private Account account1;
    private Account account2;

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
        chatRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        account1 = Instancio.of(Account.class)
                .create();
        account2 = Instancio.of(Account.class)
                .create();
        account1.setContacts(new ArrayList<>());
        account2.setContacts(new ArrayList<>());
        account1 = accountRepository.save(account1);
        account2 = accountRepository.save(account2);

        chat = new Chat();
        chat2 = new Chat();
        chatRepository.save(chat);
        chatRepository.save(chat2);

        chat.setAccounts(new ArrayList<>(List.of(account1, account2)));
        chat2.setAccounts(new ArrayList<>(List.of(account1, account2)));

        chatRepository.save(chat);
        chatRepository.save(chat2);

        message = new Message();
        message.setDate(LocalDateTime.now());
        message.setText("Sdsd123sdsds");
        message.setSender(account1);

        message.setChat(chat);

        messageRepository.save(message);
    }

    @Test
    void findByChatId() {
        //when
        List<Message> res = messageRepository.findByChatId(chat.getId(), Sort.by(Sort.Direction.ASC, "date"));

        //then
        assertEquals(List.of(message), res);
    }

    @Test
    void notFindByChatId() {
        //when
        List<Message> res = messageRepository.findByChatId(chat2.getId(), Sort.by(Sort.Direction.ASC, "date"));

        //then
        assertEquals(List.of(), res);
    }
}