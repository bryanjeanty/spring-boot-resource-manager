package com.resource.manager.resource.service;

import com.resource.manager.resource.model.AccountPrincipal;
import com.resource.manager.resource.entity.Account;
import com.resource.manager.resource.repository.AccountRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = this.accountRepository.findByUsername(username);

        AccountPrincipal accountPrincipal = new AccountPrincipal(account);

        return accountPrincipal;
    }
}