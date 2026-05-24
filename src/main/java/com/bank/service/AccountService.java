package com.bank.service;

import com.bank.dto.AccountRequest;
import com.bank.dto.TransactionRequest;

public interface AccountService {

    String createAccount(AccountRequest request, String email);

    String deposit(TransactionRequest request);

    String withdraw(TransactionRequest request);

    Double checkBalance(String accountNumber);
}