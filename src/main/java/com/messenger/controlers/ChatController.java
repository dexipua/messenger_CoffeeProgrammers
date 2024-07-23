package com.messenger.controlers;

import com.messenger.dto.chat.ChatRequest;
import com.messenger.dto.chat.ChatResponse;
import com.messenger.mapper.ChatMapper;
import com.messenger.models.Chat;
import com.messenger.services.interfaces.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chats")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @PostMapping("/create/{first_account_id}/{second_account_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse create(@PathVariable("first_account_id") Long firstAccountId,
                               @PathVariable("second_account_id") Long secondAccountId,
                               @Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.create(firstAccountId, secondAccountId);
        chat.setName(chatRequest.getName());
        return chatMapper.toResponse(chat);
    }

    @DeleteMapping("/delete/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long chatId) {
        chatService.delete(chatId);
    }

    @GetMapping("/findById/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatResponse findById(@PathVariable Long chatId) {
        return chatMapper.toResponse(chatService.findById(chatId));
    }

    @GetMapping("/findByAccountId/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponse> findByAccountId(@PathVariable Long accountId){
        return chatService.findByAccountId(accountId).stream()
                .map(chatMapper::toResponse).toList();
    }

}
