package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

    @NotBlank(message = "Account Number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")

    @Min(value = 1,
            message = "Amount must be greater than 0")

    private Double amount;

    public TransactionRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}