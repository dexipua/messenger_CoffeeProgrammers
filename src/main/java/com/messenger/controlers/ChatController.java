package com.messenger.controlers;

import com.messenger.dto.chat.ChatRequest;
import com.messenger.dto.chat.ChatResponse;
import com.messenger.mapper.ChatMapper;
import com.messenger.services.interfaces.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chats")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @PreAuthorize("@accountSecurity.checkAccounts(#auth, " +
            "#firstAccountId, #secondAccountId)")
    @PostMapping("/create/{first_account_id}/{second_account_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse create(@PathVariable("first_account_id") Long firstAccountId,
                               @PathVariable("second_account_id") Long secondAccountId,
                               @Valid @RequestBody ChatRequest chatRequest,
                               Authentication auth) {
        return chatMapper.toResponse(chatService.create(firstAccountId, secondAccountId,
                chatRequest.getName()));
    }

    @PreAuthorize("@accountSecurity.checkAccountsByChat(#auth, #chatId)")
    @DeleteMapping("/delete/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long chatId,
                       Authentication auth) {
        chatService.delete(chatId);
    }

    @PreAuthorize("@accountSecurity.checkAccountsByChat(#auth, #chatId)")
    @GetMapping("/findById/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatResponse findById(@PathVariable Long chatId,
                                 Authentication auth) {
        return chatMapper.toResponse(chatService.findById(chatId));
    }

    @PreAuthorize("@accountSecurity.checkAccount(#auth, #accountId)")
    @GetMapping("/findByAccountId/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponse> findByAccountId(@PathVariable Long accountId,
                                              Authentication auth){
        return chatService.findByAccountId(accountId).stream()
                .map(chatMapper::toResponse).toList();
    }

    @PreAuthorize("@accountSecurity.checkAccounts(#auth, " +
            "#firstAccountId, #secondAccountId)")
    @GetMapping("/exists/{first_account_id}/{second_account_id}")
    @ResponseStatus(HttpStatus.OK)
    public Long findChatsByAccountIds(@PathVariable("first_account_id") Long firstAccountId,
                                      @PathVariable("second_account_id") Long secondAccountId,
                                      Authentication auth) {
        List<Long> list = List.of(firstAccountId, secondAccountId);
        return chatService.findChatsByAccountIds(list);
    }
}
