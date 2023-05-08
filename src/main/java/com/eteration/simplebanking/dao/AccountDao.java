package com.eteration.simplebanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eteration.simplebanking.model.Account;

public interface AccountDao extends JpaRepository<Account, Long> {
	
	public Account findByAccountNumber(String accountNumber);
	

	
	


}
