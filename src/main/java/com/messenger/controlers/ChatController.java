package com.messenger.controlers;

import com.messenger.dto.chat.ChatRequest;
import com.messenger.dto.chat.ChatResponse;
import com.messenger.mapper.ChatMapper;
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
        return chatMapper.toResponse(chatService.create(firstAccountId, secondAccountId, chatRequest.getName()));
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

    @GetMapping("/exists/{first_account_id}/{second_account_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponse> findChatsByAccountIds(@PathVariable Long first_account_id,
                                      @PathVariable Long second_account_id) {
        List<Long> list = List.of(first_account_id, second_account_id);
        return chatService.findChatsByAccountIds(list).stream()
                .map(chatMapper::toResponse).toList();
    }

}
