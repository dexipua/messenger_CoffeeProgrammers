package com.messenger.controlers;

import com.messenger.dto.message.MessageRequest;
import com.messenger.dto.message.MessageResponse;
import com.messenger.mapper.MessageMapper;
import com.messenger.models.Account;
import com.messenger.models.Chat;
import com.messenger.models.Message;
import com.messenger.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/private-message")
    public MessageResponse sendPrivateMessage(MessageRequest messageRequest) {
        Message savedMessage = messageService.create(messageMapper.toModel(messageRequest));
        Chat chat = savedMessage.getChat();

        for (Account account : chat.getAccounts()) {
            messagingTemplate.convertAndSendToUser(
                    account.getId().toString(),
                    "/queue/messages",
                    messageMapper.toResponse(savedMessage)
            );
        }
        System.out.println("MESSAGE " + messageMapper.toResponse(savedMessage));
        return messageMapper.toResponse(savedMessage);
    }
}
