package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // this method is valid in this interface
    Account findByUsername(String username);
}