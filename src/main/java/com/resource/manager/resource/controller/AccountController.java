package com.resource.manager.resource.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.resource.manager.resource.entity.Account;
import com.resource.manager.resource.exception.AccountWithIdNotFoundException;
import com.resource.manager.resource.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/")
  public ResponseEntity<String> createAccount(@Valid @RequestBody Account account) {
    accountService.save(account);
    return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
  }

  @GetMapping("/")
  public List<Account> getAllAccounts() {
    return accountService.findAll();
  }

  @GetMapping("/{id}")
  public Account getAccountById(@PathVariable("id") int accountId) {
    return accountService.findById(accountId).orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateAccountById(@PathVariable("id") int accountId,
      @Valid @NotNull @RequestBody Account updates) {
    Account updatedAccount = accountService.findById(accountId)
        .orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
    updatedAccount.setUsername(updates.getUsername());
    updatedAccount.setEmail(updates.getEmail());
    updatedAccount.setPassword(updates.getPassword());

    accountService.save(updatedAccount);
    return new ResponseEntity<>("Account successfully updated", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAccountById(@PathVariable("id") int accountId) {
    Account account = accountService.findById(accountId)
        .orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
    accountService.delete(account);
    return new ResponseEntity<>("Account successfully deleted", HttpStatus.NO_CONTENT);
  }
}