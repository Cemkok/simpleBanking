package com.eteration.simplebanking.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.eteration.simplebanking.model.Transaction;



public interface TransactionDao extends JpaRepository<Transaction, Long> {
	
	
}
