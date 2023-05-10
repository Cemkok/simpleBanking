package com.eteration.simplebanking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "bill_payment_transaction_id")

public class BillPaymentTransaction extends Transaction {
	
	
	private String payee;
	
	private String billingNumber;
	
	
	public BillPaymentTransaction(String payee, String billingNumber, double amount) {
		
		this.payee=payee;
        this.billingNumber=billingNumber;
		super.amount=amount;
		super.transaction_type=this.getClass().getSimpleName();
		super.transaction_date=LocalDateTime.now();
		super.approvalCode=UUID.randomUUID();
		
	}
	
	
	

}
