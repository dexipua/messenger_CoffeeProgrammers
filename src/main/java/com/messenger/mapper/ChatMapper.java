package com.messenger.mapper;

import com.messenger.dto.chat.ChatResponse;
import com.messenger.models.Chat;
import org.mapstruct.Mapper;

@Mapper
public interface ChatMapper {
    ChatResponse toResponse(Chat chat);
}
