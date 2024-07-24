package com.messenger.controlers;


import com.messenger.dto.account.AccountRequest;
import com.messenger.dto.account.AccountResponse;
import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.mapper.AccountMapper;
import com.messenger.services.interfaces.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> getAll(){
        return accountService.findAll().stream()
                .map(accountMapper::toResponseSimple).toList();
    }

    @GetMapping("/getAllContacts/{my_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> getAllContacts(
            @PathVariable("my_id") long myId){
        return accountService.findAllContacts(myId).stream()
                .map(accountMapper::toResponseSimple).toList();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse update(
            @RequestBody @Valid AccountRequest accountRequest,
            @PathVariable long id){
        return accountMapper.toResponse(accountService.update(
                accountMapper.toModel(accountRequest), id));
    }

    @GetMapping("/getAllByName")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> getAllByName(@RequestParam String lastName,
                                                    @RequestParam String firstName){
        return accountService.findByNames(lastName, firstName).stream()
                .map(accountMapper::toResponseSimple).toList();
    }
}
