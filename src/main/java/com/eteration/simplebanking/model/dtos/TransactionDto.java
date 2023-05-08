package com.eteration.simplebanking.model.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class TransactionDto {
	
	 
    @Min(value = 1, message = "Value must be greater than or equal to 1")
    @Max(value = 100, message = "Value must be less than or equal to 100")
    private double amount;

}
