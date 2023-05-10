package com.eteration.simplebanking.exception.customException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class NegativeValueEntryException extends Exception{
	
String message= "Negative value is not acceptable. Please do not enter negative values.";
	


	public NegativeValueEntryException(String message) {
		
		this.message = message;
	}
	

}
