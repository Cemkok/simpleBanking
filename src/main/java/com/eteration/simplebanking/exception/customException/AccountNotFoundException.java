package com.eteration.simplebanking.exception.customException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountNotFoundException  extends Exception{
	
	String message= "The user of the relevant account number could not be found in the database.";
	


	public AccountNotFoundException(String message) {
		
		this.message = message;
	}
	
	
}
