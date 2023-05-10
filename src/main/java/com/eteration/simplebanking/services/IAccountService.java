package com.eteration.simplebanking.services;


import org.springframework.stereotype.Service;

import com.eteration.simplebanking.exception.customException.AccountNotFoundException;
import com.eteration.simplebanking.exception.customException.CustomerAlreadyExistException;
import com.eteration.simplebanking.exception.customException.InsufficientBalanceException;
import com.eteration.simplebanking.exception.customException.NegativeValueEntryException;
import com.eteration.simplebanking.model.dtos.AccountDto;
import com.eteration.simplebanking.utils.dtoResult.Response;


@Service 
public interface IAccountService {

	Object saveAccount(AccountDto accountDto) throws CustomerAlreadyExistException;
	Object getAccount(String accountNumber) throws AccountNotFoundException;
	Response credit(String accountNumber, double amount) throws AccountNotFoundException;
	Response debit(String accountNumber, double amount) throws InsufficientBalanceException, NegativeValueEntryException, AccountNotFoundException;
	Response billPayment(String accountNumber, double amount, String payee, String billingNumber) throws InsufficientBalanceException, AccountNotFoundException;
	

}
