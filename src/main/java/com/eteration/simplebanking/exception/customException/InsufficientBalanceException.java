package com.eteration.simplebanking.exception.customException;

import com.eteration.simplebanking.model.DepositTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// This class is a place holder you can change the complete implementation
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class InsufficientBalanceException extends Exception {
	
	String message= "Your balance is insufficient. Please do not submit requests over your balance.";
	


	public InsufficientBalanceException(String message) {
		
		this.message = message;
	}
	
	

}
