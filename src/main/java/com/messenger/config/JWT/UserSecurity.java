package com.messenger.config.JWT;

import com.messenger.models.Account;
import com.messenger.services.interfaces.AccountService;
import com.messenger.services.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("accountSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final AccountService accountService;
    private final ChatService chatService;

    public boolean checkAccount(Authentication authentication, long userId) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String authenticatedUserEmail = user.getUsername();
        String ownEmail = accountService.findById(userId).getEmail();
        return authenticatedUserEmail.equals(ownEmail);
    }

    public boolean checkAccounts(Authentication auth, long oneId, long twoId) {
        return checkAccount(auth, oneId) || checkAccount(auth, twoId);
    }

    public boolean checkAccountsByChat(Authentication auth, long chatId) {
        List<Account> list = chatService.findById(chatId).getAccounts();
        return list.stream().anyMatch(account -> checkAccount(auth, account.getId()));
    }
}
