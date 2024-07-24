package com.messenger.repository;

import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Role;
import com.messenger.models.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        chatRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void findAllByAccountId() {
        // given
        Account account = new Account();
        account.setEmail("email@gmil.com");
        account.setPassword("passWord1");
        account.setDescription("Some");
        account.setFirstName("First");
        account.setLastName("Last");
        account.setRole(Role.USER);
        account.setStatus(Status.ONLINE);
        accountRepository.save(account);

        Chat chat1 = new Chat();
        chat1.getAccounts().add(account);
        Chat chat2 = new Chat();
        chat2.getAccounts().add(account);
        chatRepository.save(chat1);
        chatRepository.save(chat2);

        accountRepository.save(account);

        // when
        List<Chat> foundChats = chatRepository.findAllByAccountId(account.getId());

        // then
        assertThat(foundChats).hasSize(2);
        assertThat(foundChats).containsExactlyInAnyOrder(chat1, chat2);
    }
}
