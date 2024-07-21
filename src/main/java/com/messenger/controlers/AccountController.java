package com.messenger.controlers;


import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> getAll(){
        return accountService.getAll();
    }
}
