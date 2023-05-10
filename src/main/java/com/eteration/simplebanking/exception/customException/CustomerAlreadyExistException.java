package com.eteration.simplebanking.exception.customException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerAlreadyExistException extends Exception{
	
	String message= "This account already exists.";
	


	public CustomerAlreadyExistException(String message) {
		
		this.message = message;
	}
	
	

}


