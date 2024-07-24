package com.messenger.controlers;


import com.messenger.dto.account.AccountListResponseDTO;
import com.messenger.dto.account.AccountRequest;
import com.messenger.dto.account.AccountResponse;
import com.messenger.dto.account.AccountResponseSimple;
import com.messenger.mapper.AccountMapper;
import com.messenger.models.Account;
import com.messenger.services.interfaces.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public AccountListResponseDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        AccountListResponseDTO responseDTO = new AccountListResponseDTO();
        Object[] objects = accountService.findAll(page, size);
        responseDTO.setList(((List<Account>) objects[0]).stream()
                .map(accountMapper::toResponseSimple).toList());
        responseDTO.setPages((int) objects[1] - 1);
        return responseDTO;
    }

    @GetMapping("/getAllContacts/{my_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> getAllContacts(
            @PathVariable("my_id") long myId) {
        return accountService.findAllContacts(myId).stream()
                .map(accountMapper::toResponseSimple).toList();
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse update(
            @RequestBody @Valid AccountRequest accountRequest,
            @PathVariable long id) {
        return accountMapper.toResponse(accountService.update(
                accountMapper.toModel(accountRequest), id));
    }

    @GetMapping("/getAllByName")
    @ResponseStatus(HttpStatus.OK)
    public AccountListResponseDTO getAllByName(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam String lastName,
            @RequestParam String firstName) {
        AccountListResponseDTO responseDTO = new AccountListResponseDTO();
        Object[] objects = accountService.findByNames(lastName, firstName, page, size);
        responseDTO.setList(((List<Account>) objects[0]).stream()
                .map(accountMapper::toResponseSimple).toList());
        responseDTO.setPages((int) objects[1] - 1);
        return responseDTO;
    }

    @PostMapping("/{id}/addContact/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse addContact(@PathVariable long id, @PathVariable long contactId){
        return accountMapper.toResponse(accountService.addContact(id, contactId));
    }

    @DeleteMapping("/{id}/removeContact/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse removeContact(@PathVariable long id, @PathVariable long contactId){
        return accountMapper.toResponse(accountService.removeContact(id, contactId));
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse findById(@PathVariable long id) {
        System.out.println(id);
        return accountMapper.toResponse(accountService.findById(id));
    }

    @GetMapping("/notInContactList/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseSimple> findAccountsNotInContactList(@PathVariable Long accountId,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        return accountService.findAccountsNotInContactList(accountId, page, size).stream()
                .map(accountMapper::toResponseSimple).toList();
    }
}
