package com.eteration.simplebanking.utils.dtoResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorTransactionResponseDto extends Response {
	
	public String message;
	public String status;
	
	

}
