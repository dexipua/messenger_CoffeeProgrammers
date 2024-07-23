package com.messenger.mapper;

import com.messenger.dto.message.MessageRequest;
import com.messenger.dto.message.MessageResponse;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AccountService.class, ChatService.class})
public abstract class MessageMapper {
    private AccountService accountService;
    private ChatService chatService;

    @Mapping(target = "sender",
            expression = "java(getSender(messageRequest.getSenderId()))")
    @Mapping(target = "chat",
            expression = "java(getChat(messageRequest.getChatId()))")
    public abstract Message toModel(MessageRequest messageRequest);

    @Mapping(dateFormat = "yyyy-MM-dd HH:mm:ss", target = "date", source = "date")
    public abstract MessageResponse toResponse(Message message);

    protected Account getSender(Long senderId) {
        return accountService.findById(senderId);
    }
    protected Chat getChat(Long chatId) {
        return chatService.findById(chatId);
    }
}