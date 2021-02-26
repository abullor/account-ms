package com.reloadly.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reloadly.exception.AccountNotFoundException;
import com.reloadly.model.Account;
import com.reloadly.repository.AccountRepository;

@Service
public class AccountService {
	private final AccountRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final RabbitMessagingTemplate template;
	@Value("${app.queue}")
	private String queue;
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder, RabbitMessagingTemplate template) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.template = template;
	}
	
	@Transactional
	public Account save(Account account) {
		account.setActive(Boolean.TRUE);
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		
		Account response = this.repository.save(account);
		
		this.generateNotification(account, "account_created");
		
		return response;
	}
	
	@Transactional
	public Account update(Account account) {
		return this.repository.findById(account.getId()).map(found -> {
			if (account.getEmail() != null) {
				found.setEmail(account.getEmail());
			}
			
			if (account.getPhone() != null) {
				found.setPhone(account.getPhone());
			}
			
			if (account.getNotificationPreference() != null) {
				found.setNotificationPreference(account.getNotificationPreference());
			}
			
			Account response = this.repository.save(found);
			
			this.generateNotification(account, "account_updated");
			
			return response;
		}).orElseThrow(() -> {
			throw new AccountNotFoundException(account.getId());
		});
	}
	
	public Account findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> {
			throw new AccountNotFoundException(id);
		});
	}
	
	private void generateNotification(Account account, String event) {
		StringBuilder message = new StringBuilder(account.getId().toString()).append(",").append(event);
		
		try {
			template.convertAndSend(queue, message.toString());
		} catch (MessagingException e) {
			LOGGER.error("Notification failed.", e);
		}
	}
}