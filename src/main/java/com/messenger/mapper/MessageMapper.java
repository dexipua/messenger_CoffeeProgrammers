package com.messenger.mapper;

import com.messenger.dto.message.MessageRequest;
import com.messenger.dto.message.MessageResponse;
import com.messenger.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {
    Message toModel(MessageRequest messageRequest);
    @Mapping(source = "account", target = "accountResponse")
    @Mapping(dateFormat = "yyyy-MM-dd HH:mm:ss", target = "date", source = "date")
    MessageResponse toResponse(Message message);
    MessageRequest toMessageRequest(Message message);
}