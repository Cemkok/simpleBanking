package com.eteration.simplebanking.model.dtos.request;

import lombok.Data;

@Data
public class BillPaymentRequestDto {

	public double amount;
	public String payee;
	public String billingNumber;

}
