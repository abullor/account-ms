package com.reloadly.service;

import com.reloadly.exception.AccountNotFoundException;
import com.reloadly.model.Account;
import com.reloadly.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

public class AccountServiceTest {
    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository repository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RabbitMessagingTemplate template;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(accountService, "queue", "queue");
    }

    @Test
    public void givenAccountWhenSaveThenVerify() {
        Account accountMock = Mockito.mock(Account.class);

        Mockito.when(accountMock.getPassword()).thenReturn("PLAIN");
        Mockito.when(repository.save(accountMock)).thenReturn(accountMock);
        Mockito.when(passwordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("ENCRYPTED");
        Mockito.doNothing().when(template).convertAndSend(Mockito.anyString(), Mockito.anyString());

        Account response = accountService.save(accountMock);

        Assertions.assertEquals(accountMock, response);

        Mockito.verify(repository, Mockito.times(1)).save(accountMock);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.any(CharSequence.class));
        Mockito.verify(template, Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void givenNonExistentAccountWhenUpdateThenThrowAccountNotFoundException() {
        Account accountMock = Mockito.mock(Account.class);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.update(accountMock));

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Account.class));
        Mockito.verify(template, Mockito.times(0)).convertAndSend(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void givenExistentAccountWhenUpdateThenVerify() {
        Account accountMock = Mockito.mock(Account.class);

        Mockito.when(repository.findById(0L)).thenReturn(Optional.of(accountMock));
        Mockito.when(repository.save(accountMock)).thenReturn(accountMock);

        Account response = accountService.update(accountMock);

        Assertions.assertEquals(accountMock, response);

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(repository, Mockito.times(1)).save(accountMock);
        Mockito.verify(template, Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void givenNonExistentAccountWhenFindByIdThenThrowAccountNotFoundException() {
        Account accountMock = Mockito.mock(Account.class);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findById(1L));

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void givenExistentAccountWhenFindByIdThenVerify() {
        Account accountMock = Mockito.mock(Account.class);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountMock));

        Account response = accountService.findById(1L);

        Assertions.assertEquals(accountMock, response);

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyLong());
    }
}