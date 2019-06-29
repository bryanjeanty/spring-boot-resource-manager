package com.resource.manager.resource.service;

import java.util.List;
import java.util.Optional;

import com.resource.manager.resource.entity.Account;
import com.resource.manager.resource.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  // save record
  public Account save(Account account) {
    return accountRepository.save(account);
  }

  // find all records
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  // find specific record
  public Optional<Account> findById(int accountId) {
    return accountRepository.findById(accountId);
  }

  // delete a record
  public void delete(Account account) {
    accountRepository.delete(account);
  }
}