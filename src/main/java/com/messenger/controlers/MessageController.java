package com.messenger.controlers;

import com.messenger.dto.message.MessageResponse;
import com.messenger.mapper.MessageMapper;
import com.messenger.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PreAuthorize("@accountSecurity.checkAccountsByChat(#auth, #chatId)")
    @GetMapping("/getByChat/{chat_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponse> getMessagesByChatId(@PathVariable("chat_id") Long chatId,
                                                     Authentication auth) {
        return messageService.getAllByChatId(chatId).stream()
                .map(messageMapper::toResponse).toList();
    }
}
