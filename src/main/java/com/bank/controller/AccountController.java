package com.bank.controller;

import com.bank.dto.AccountRequest;
import com.bank.dto.TransactionRequest;
import com.bank.dto.TransferRequest;
import com.bank.security.JwtUtil;
import com.bank.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.dto.TransactionResponse;
import java.util.List;

@RestController
@RequestMapping("/api/account")

public class AccountController {

	private final AccountService accountService;
	private final JwtUtil jwtUtil;

	public AccountController(AccountService accountService, JwtUtil jwtUtil) {

		this.accountService = accountService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createAccount(

			@Valid	@RequestBody AccountRequest request, @RequestHeader("Authorization") String authHeader) {

		String token = authHeader.substring(7);

		String email = jwtUtil.extractEmail(token);

		String response = accountService.createAccount(request, email);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/deposit")
	public ResponseEntity<String> depositMoney(

			@Valid	@RequestBody TransactionRequest request) {

		String response = accountService.deposit(request);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<String> withdrawMoney(

			@Valid	@RequestBody TransactionRequest request) {

		String response = accountService.withdraw(request);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/balance/{accountNumber}")
	public ResponseEntity<Double> checkBalance(

			@PathVariable String accountNumber) {

		Double balance = accountService.checkBalance(accountNumber);

		return ResponseEntity.status(HttpStatus.OK).body(balance);
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transferMoney(

			@Valid	@RequestBody TransferRequest request) {

		String response = accountService.transferMoney(request);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/transactions/{accountNumber}")

	public ResponseEntity<List<TransactionResponse>>
	getTransactionHistory(

	        @PathVariable String accountNumber
	) {

	    List<TransactionResponse> transactions =
	            accountService.getTransactionHistory(
	                    accountNumber
	            );

	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(transactions);
	}
}