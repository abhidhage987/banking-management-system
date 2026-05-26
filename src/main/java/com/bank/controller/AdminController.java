package com.bank.controller;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	public AdminController(UserRepository userRepository, AccountRepository accountRepository) {

		this.userRepository = userRepository;
		
		this.accountRepository = accountRepository;
	}
	
	
	

	@Operation(summary = "Get All Users", description = "Admin can fetch all users")

	@GetMapping("/users")

	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = userRepository.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	

	@Operation(summary = "Get All Accounts", description = "Admin can fetch all bank accounts")

	@GetMapping("/accounts")

	public ResponseEntity<List<Account>> getAllAccounts() {

		List<Account> accounts = accountRepository.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(accounts);
	}
	
	

	@Operation(summary = "Delete Account", description = "Admin can delete account")

	@DeleteMapping("/account/{id}")

	public ResponseEntity<String> deleteAccount(

			@PathVariable Long id) {

		accountRepository.deleteById(id);

		return ResponseEntity.status(HttpStatus.OK).body("Account Deleted Successfully");
	}
}