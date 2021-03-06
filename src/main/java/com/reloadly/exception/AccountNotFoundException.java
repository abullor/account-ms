package com.reloadly.exception;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found")
public class AccountNotFoundException extends RuntimeException implements Supplier<AccountNotFoundException> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4423542321959613348L;

	public AccountNotFoundException(Long id) {
	    super(String.format("Account [%s] not found.", id));
	}
	
	@Override
    public AccountNotFoundException get() {
        return this;
    }
}
