package com.messenger.config;

import com.messenger.mapper.AccountMapper;
import com.messenger.mapper.ChatMapper;
import com.messenger.mapper.MessageMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public AccountMapper accountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }
    @Bean
    public MessageMapper messageMapper() {
        return Mappers.getMapper(MessageMapper.class);
    }
    @Bean
    public ChatMapper chatMapper() {
        return Mappers.getMapper(ChatMapper.class);
    }
}
