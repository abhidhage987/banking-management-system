package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountRequest {

    @NotBlank(message = "Account Type is required")
    private String accountType;

    @NotNull(message = "Initial Balance is required")

    @Min(value = 1,
            message = "Initial Balance must be greater than 0")

    private Double initialBalance;

    public AccountRequest() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }
}