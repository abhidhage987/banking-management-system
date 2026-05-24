package com.bank.serviceImpl;

import com.bank.dto.AccountRequest;
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
import com.bank.repository.TransactionRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Random;

@Service

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
            UserRepository userRepository,
            TransactionRepository transactionRepository) {

this.accountRepository = accountRepository;
this.userRepository = userRepository;
this.transactionRepository = transactionRepository;
}
    @Override
    public String createAccount(AccountRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setBalance(request.getInitialBalance());
        account.setAccountType(request.getAccountType());
        account.setStatus(AccountStatus.ACTIVE);
        account.setUser(user);

        accountRepository.save(account);

        return "Account Created Successfully";
    }

    
    private String generateAccountNumber() {

        Random random = new Random();

        return "ACC" + (100000 + random.nextInt(900000));
    }
    
    @Override
    public String deposit(TransactionRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(
                account.getBalance() + request.getAmount()
        );

        accountRepository.save(account);

        return "Amount Deposited Successfully";
    }
    
    @Override
    public String withdraw(TransactionRequest request) {

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if(account.getBalance() < request.getAmount()) {

            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(
                account.getBalance() - request.getAmount()
        );

        accountRepository.save(account);

        return "Amount Withdrawn Successfully";
    }
    
    @Override
    public Double checkBalance(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return account.getBalance();
    }
    
    @Override
    @Transactional

    public String transferMoney(TransferRequest request) {

        Account senderAccount = accountRepository
                .findByAccountNumber(request.getSenderAccount())
                .orElseThrow(() ->
                        new RuntimeException("Sender Account Not Found"));

        Account receiverAccount = accountRepository
                .findByAccountNumber(request.getReceiverAccount())
                .orElseThrow(() ->
                        new RuntimeException("Receiver Account Not Found"));

        
        if(senderAccount.getBalance() < request.getAmount()) {

            throw new RuntimeException("Insufficient Balance");
        }

       
        senderAccount.setBalance(
                senderAccount.getBalance() - request.getAmount()
        );

        
        receiverAccount.setBalance(
                receiverAccount.getBalance() + request.getAmount()
        );

        
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
    
}