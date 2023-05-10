package com.eteration.simplebanking.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.utils.dtoResult.ErrorTransactionResponseDto;
import com.eteration.simplebanking.utils.dtoResult.Response;
import com.eteration.simplebanking.utils.dtoResult.TransactionResponseDto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class TestAccountService implements ITestAccountService {
	
	

	 private final Map<String, Account> accounts = new HashMap<>();

	    @Override
	    public Account findAccount(String accountNumber) {
	        return accounts.get(accountNumber);
	    }

	    @Override
	    public void saveAccount(Account account) {
	        accounts.put(account.getAccountNumber(), account);
	    }
	}
