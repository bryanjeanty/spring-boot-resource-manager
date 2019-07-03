package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Account;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Integer> {
    // this method is valid in this interface
    Account findByUsername(String username);
}