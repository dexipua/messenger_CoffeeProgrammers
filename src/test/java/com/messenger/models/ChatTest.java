package com.messenger.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ChatTest {

    @Test
    void testEquals() {
        // given
        Chat chat1 = new Chat(1L, "ffdsgdsfsdsfd", List.of(new Account()));
        Chat chat2 = new Chat(1L, "ffdsgdsfsdsfd", List.of(new Account()));
        Chat chat3 = new Chat(3L,"sfddssfd", null);

        // then
        assertThat(chat1).isEqualTo(chat2);
        assertThat(chat1).isNotEqualTo(chat3);
        assertNotEquals(chat1, null);
        assertNotEquals(chat1, new Object());
    }

    @Test
    void testHashCode() {
        // given
        Chat chat1 = new Chat(1L, "adadadad", List.of(new Account()));
        Chat chat2 = new Chat(1L, "adadadad", List.of(new Account()));

        Chat chat3 = new Chat(2L, "adadadad", null);

        // then
        assertThat(chat1.hashCode()).isEqualTo(chat2.hashCode());
        assertThat(chat1.hashCode()).isNotEqualTo(chat3.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithCollections() {
        // given
        Chat chat1 = new Chat(1L, "adadadad", List.of(new Account()));
        Chat chat2 = new Chat(1L, "adadadad", List.of(new Account()));

        Chat chat3 = new Chat(2L, "adadadad", null);
        // then
        assertThat(chat1).isEqualTo(chat2);
        assertThat(chat1.hashCode()).isEqualTo(chat2.hashCode());
        assertThat(chat1.hashCode()).isNotEqualTo(chat3.hashCode());
    }

    @Test
    void testToString() {
        // given
        Chat chat = new Chat(1L, "sadsadaf", null);

        // when
        String result = chat.toString();

        // then
        assertThat(result).contains("Chat");
        assertThat(result).contains("id=1");
    }
}
