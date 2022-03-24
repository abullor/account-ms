package com.reloadly.service;

import com.reloadly.model.Account;
import com.reloadly.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JWTUserDetailsServiceTest {
    @InjectMocks
    JWTUserDetailsService jwtUserDetailsService;

    @Mock
    AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenNonExistentUsernameWhenLoadUserByUsernameThenThrowUsernameNotFoundException() {
        Mockito.when(accountRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("username"));

        Mockito.verify(accountRepository, Mockito.times(1)).findByName(Mockito.anyString());
    }

    @Test
    public void givenExistentUsernameWhenLoadUserByUsernameThenVerify() {
        Account account = new Account();
        account.setName("user");
        account.setPassword("user");
        account.setId(1L);

        Mockito.when(accountRepository.findByName("user")).thenReturn(Optional.of(account));

        UserDetails response = jwtUserDetailsService.loadUserByUsername("user");

        Assertions.assertEquals(response.getUsername(), account.getId().toString());
        Assertions.assertEquals(response.getPassword(), account.getPassword());

        Mockito.verify(accountRepository, Mockito.times(1)).findByName("user");
    }
}
