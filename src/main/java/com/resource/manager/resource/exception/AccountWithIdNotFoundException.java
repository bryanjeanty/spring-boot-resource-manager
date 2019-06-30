package com.resource.manager.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountWithIdNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    private int accountId;

    public AccountWithIdNotFoundException(int accountId) {
        super(String.format("Account with id [%s] was not found!", accountId));
        this.accountId = accountId;
    }

    public int getAccountId() {
        return this.accountId;
    }
}