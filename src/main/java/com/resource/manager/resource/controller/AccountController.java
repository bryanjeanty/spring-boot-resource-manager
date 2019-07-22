package com.resource.manager.resource.controller;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.resource.manager.resource.entity.Account;
import com.resource.manager.resource.exception.AccountWithIdNotFoundException;
import com.resource.manager.resource.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService) {
      this.accountService = accountService;
    }

  @PostMapping("/register")
  @SuppressWarnings({ "unchecked", "rawtypes"})  
  public @ResponseBody ResponseEntity<Map> createAccount(@Valid @RequestBody Account account) {
    String tempPassword = account.getPassword();
    account.setPassword(passwordEncoder.encode(tempPassword));
    accountService.save(account);
    
    Map response = new LinkedHashMap();
    
    response.put("message", "Account successfully created");
    response.put("body", account);
    return ResponseEntity.accepted().body(response);
  }

  @GetMapping("/auth/accounts")
  public List<Account> getAllAccounts() {
    return accountService.findAll();
  }

  @GetMapping("/auth/accounts/{id}")
  public Account getAccountById(@PathVariable("id") int accountId) {
    return accountService.findById(accountId).orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
  }

  @PutMapping("/auth/accounts/{id}")
  @SuppressWarnings({ "unchecked", "rawtypes"})  
  public @ResponseBody ResponseEntity<Map> updateAccountById(@PathVariable("id") int accountId,
      @Valid @NotNull @RequestBody Account updates) {
    Account updatedAccount = accountService.findById(accountId)
        .orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
    updatedAccount.setUsername(updates.getUsername());
    updatedAccount.setEmail(updates.getEmail());
    updatedAccount.setPassword(passwordEncoder.encode(updates.getPassword()));
    accountService.save(updatedAccount);
    
    Map response = new LinkedHashMap();
    
    response.put("message", "Account successfully updated");
    response.put("body", updatedAccount);    
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/auth/accounts/{id}")
  @SuppressWarnings({ "unchecked", "rawtypes"})  
  public @ResponseBody ResponseEntity<Map> deleteAccountById(@PathVariable("id") int accountId) {
    Account account = accountService.findById(accountId)
        .orElseThrow(() -> new AccountWithIdNotFoundException(accountId));
    accountService.delete(account);
    
    Map response = new LinkedHashMap();
    
    response.put("message", "Account successfully deleted");
    response.put("body", account);
    return ResponseEntity.ok().body(response);
  }
}