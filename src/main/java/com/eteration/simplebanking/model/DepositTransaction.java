package com.eteration.simplebanking.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


// This class is a place holder you can change the complete implementation
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@PrimaryKeyJoinColumn(name = "deposit_transaction_id")
public class DepositTransaction extends Transaction{
	
	
	public DepositTransaction (double amount) {
		super.amount=amount;
		
	}
	
	
	

	
}
