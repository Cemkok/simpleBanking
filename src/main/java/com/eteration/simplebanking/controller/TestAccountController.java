
package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.exception.customException.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.services.ITestAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestAccountController {

    @Autowired
    private ITestAccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction depositTransaction) {
        Account account = accountService.findAccount(accountNumber);
        account.deposit(depositTransaction.getAmount());
        accountService.saveAccount(account);
        TransactionStatus transactionStatus = new TransactionStatus("OK");
        return new ResponseEntity<>(transactionStatus, HttpStatus.OK);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction withdrawalTransaction) throws InsufficientBalanceException {
        Account account = accountService.findAccount(accountNumber);
        account.withdraw(withdrawalTransaction.getAmount());
        accountService.saveAccount(account);
        TransactionStatus transactionStatus = new TransactionStatus("OK");
        return new ResponseEntity<>(transactionStatus, HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
