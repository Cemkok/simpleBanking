package com.eteration.simplebanking.services;


import org.springframework.stereotype.Service;


import com.eteration.simplebanking.model.dtos.AccountDto;
import com.eteration.simplebanking.utils.dtoResult.Response;


@Service
public interface IAccountService {

	Object saveAccount(AccountDto accountDto);
	Object getAccount(String accountNumber);
	Response credit(String accountNumber, double amount);
	Response debit(String accountNumber, double amount);
	Response billPayment(String accountNumber, double amount, String payee, String billingNumber);
	

}
