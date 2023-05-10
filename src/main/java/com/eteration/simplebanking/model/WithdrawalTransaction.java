package com.eteration.simplebanking.model;

import java.time.LocalDateTime;
import java.util.UUID;

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
@PrimaryKeyJoinColumn(name = "withdrawal_transaction_Id")
public class WithdrawalTransaction extends Transaction {
	
	
	public WithdrawalTransaction(double amount) {
		super.amount=amount;
		super.transaction_type=this.getClass().getSimpleName();
		super.transaction_date=LocalDateTime.now();
		super.approvalCode=UUID.randomUUID();
	
	}

}


