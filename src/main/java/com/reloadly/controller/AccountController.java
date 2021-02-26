package com.reloadly.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.exposition.FindAccountResponse;
import com.reloadly.exposition.LoginRequest;
import com.reloadly.exposition.LoginResponse;
import com.reloadly.exposition.SaveAccountRequest;
import com.reloadly.exposition.SaveAccountResponse;
import com.reloadly.exposition.UpdateAccountRequest;
import com.reloadly.model.Account;
import com.reloadly.security.JWTProvider;
import com.reloadly.service.AccountService;
import com.reloadly.service.JWTUserDetailsService;

@RestController
public class AccountController {
	private final AccountService accountService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;
	@Autowired
    private JWTProvider jwtTokenUtil;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/accounts")
	public SaveAccountResponse save(@RequestBody @Valid SaveAccountRequest request) {
		return modelMapper.map(this.accountService.save(modelMapper.map(request, Account.class)),
				SaveAccountResponse.class);
	}

	@PutMapping("/accounts/{id}")
	public void update(@RequestBody @Valid UpdateAccountRequest request, @PathVariable Long id) {
		Account account = modelMapper.map(request, Account.class);
		account.setId(id);

		this.accountService.update(account);
	}

	@GetMapping("/accounts/{id}")
	public FindAccountResponse findById(@PathVariable Long id) {
		return modelMapper.map(this.accountService.findById(id), FindAccountResponse.class);
	}

	@PostMapping("/login")
	public LoginResponse createAuthenticationToken(@RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getName(),
				request.getPassword());
		try {
			authenticationManager.authenticate(token);
		} catch (BadCredentialsException e) {
			throw e;
		}

		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getName());

		return new LoginResponse(jwtTokenUtil.generateToken(userDetails));
	}
}