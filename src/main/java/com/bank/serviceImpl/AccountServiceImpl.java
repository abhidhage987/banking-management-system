package com.bank.serviceImpl;

import com.bank.dto.AccountRequest;
import org.springframework.cache.annotation.Cacheable;
import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.enums.AccountStatus;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.service.AccountService;
import org.springframework.stereotype.Service;
import com.bank.dto.TransactionRequest;
import com.bank.dto.TransferRequest;
import com.bank.entity.Transaction;
import com.bank.enums.TransactionType;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.repository.TransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Random;

import com.bank.dto.TransactionResponse;
import java.util.ArrayList;
import java.util.List;

@Service

public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;

	public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository,
			TransactionRepository transactionRepository) {

		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override

	public String createAccount(
	        AccountRequest request,
	        String email
	) {

	   

	    User user = userRepository

	            .findByEmail(email)

	            .orElseThrow(() ->

	                    new ResourceNotFoundException(
	                            "User not found"
	                    )
	            );

	    

	    Account account = new Account();

	    

	    account.setAccountNumber(
	            generateAccountNumber()
	    );

	    

	    account.setBalance(
	            request.getInitialBalance()
	    );

	    account.setAccountType(
	            request.getAccountType()
	    );

	    account.setStatus(
	            AccountStatus.ACTIVE
	    );

	    

	    account.setUser(user);

	    
	    accountRepository.save(account);

	    return "Account Created Successfully";
	}
	private String generateAccountNumber() {

		Random random = new Random();

		return "ACC" + (100000 + random.nextInt(900000));
	}

	@Override

	@CacheEvict(
	        value = "accountBalance",
	        key = "#request.accountNumber"
	)

	public String deposit(TransactionRequest request) {

	    Account account = accountRepository
	            .findByAccountNumber(request.getAccountNumber())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Account not found"
	                    ));

	    account.setBalance(
	            account.getBalance() + request.getAmount()
	    );

	    accountRepository.save(account);

	    return "Amount Deposited Successfully";
	}

	@Override

	@CacheEvict(
	        value = "accountBalance",
	        key = "#request.accountNumber"
	)

	public String withdraw(TransactionRequest request) {

	    Account account = accountRepository
	            .findByAccountNumber(request.getAccountNumber())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Account not found"
	                    ));

	    if(account.getBalance() < request.getAmount()) {

	        throw new InsufficientBalanceException(
	                "Insufficient Balance"
	        );
	    }

	    account.setBalance(
	            account.getBalance() - request.getAmount()
	    );

	    accountRepository.save(account);

	    return "Amount Withdrawn Successfully";
	}

	@Override

	@Cacheable(value = "accountBalance", key = "#accountNumber")

	public Double checkBalance(String accountNumber) {

		System.out.println("Fetching Balance From Database...");

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		return account.getBalance();
	}

	@Override

	@Transactional

	@Caching(
	        evict = {

	                @CacheEvict(
	                        value = "accountBalance",
	                        key = "#request.senderAccount"
	                ),

	                @CacheEvict(
	                        value = "accountBalance",
	                        key = "#request.receiverAccount"
	                )
	        }
	)
	public String transferMoney(TransferRequest request) {

		Account senderAccount = accountRepository.findByAccountNumber(request.getSenderAccount())
				.orElseThrow(() -> new ResourceNotFoundException("Sender Account Not Found"));

		Account receiverAccount = accountRepository.findByAccountNumber(request.getReceiverAccount())
				.orElseThrow(() -> new ResourceNotFoundException("Receiver Account Not Found"));

		if (senderAccount.getBalance() < request.getAmount()) {

			throw new InsufficientBalanceException("Insufficient Balance");
		}

		senderAccount.setBalance(senderAccount.getBalance() - request.getAmount());

		receiverAccount.setBalance(receiverAccount.getBalance() + request.getAmount());

		accountRepository.save(senderAccount);
		accountRepository.save(receiverAccount);

		Transaction transaction = new Transaction();

		transaction.setSenderAccount(request.getSenderAccount());
		transaction.setReceiverAccount(request.getReceiverAccount());
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.TRANSFER);
		transaction.setTransactionDate(LocalDateTime.now());

		transactionRepository.save(transaction);

		return "Money Transferred Successfully";
	}

	@Override

	public List<TransactionResponse> getTransactionHistory(String accountNumber) {

		List<Transaction> transactions = transactionRepository.findBySenderAccountOrReceiverAccount(accountNumber,
				accountNumber);

		List<TransactionResponse> responseList = new ArrayList<>();

		for (Transaction transaction : transactions) {

			TransactionResponse response = new TransactionResponse();

			response.setSenderAccount(transaction.getSenderAccount());

			response.setReceiverAccount(transaction.getReceiverAccount());

			response.setAmount(transaction.getAmount());

			response.setTransactionType(transaction.getTransactionType().name());

			response.setTransactionDate(transaction.getTransactionDate());

			responseList.add(response);
		}

		return responseList;
	}

}