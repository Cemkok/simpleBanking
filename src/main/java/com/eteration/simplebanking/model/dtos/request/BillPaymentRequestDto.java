package com.eteration.simplebanking.model.dtos.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class BillPaymentRequestDto {
	
	@Min(value = 0, message = "Amount cannot be negative")
	@Max(value = 10000, message = "Amount limit exceeded. The maximum amount is 10,000")
	public double amount;
	public String payee;
	public String billingNumber;

}
