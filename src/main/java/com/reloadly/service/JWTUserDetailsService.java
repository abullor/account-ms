package com.reloadly.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reloadly.model.Account;
import com.reloadly.repository.AccountRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Account> account = this.repository.findByName(name);
        
        if (!account.isPresent()) {
            throw new UsernameNotFoundException(String.format("Account not found with name [%s].", name));
        }

        return new User(account.get().getId().toString(), account.get().getPassword(), new ArrayList<>());
    }
}