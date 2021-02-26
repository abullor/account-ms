package com.reloadly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reloadly.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByName(String name);
}