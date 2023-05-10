package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Account;


public interface ITestAccountService {
	  Account findAccount(String accountNumber);
	    void saveAccount(Account account);

}
