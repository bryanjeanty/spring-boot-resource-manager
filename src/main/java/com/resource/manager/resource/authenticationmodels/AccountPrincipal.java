package com.resource.manager.resource.authenticationmodels;

import java.util.Collection;
import java.util.HashSet;

import com.resource.manager.resource.entity.Account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Account account;

    public AccountPrincipal(Account account) {
        this.account = account;
    }

    // return an empty HashSet as we have not defined any user
    // roles in our aapplication
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // checks if the current user is active or not
    // ie. the session/token has not expired
    @Override
    public boolean isEnabled() {
        return true;
    }
}