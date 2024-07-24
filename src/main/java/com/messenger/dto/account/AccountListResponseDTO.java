package com.messenger.dto.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountListResponseDTO {
    private int pages;
    private List<AccountResponseSimple> list;
}
