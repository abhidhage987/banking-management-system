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

import java.util.Random;

@Service

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository) {

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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
    
}