package com.eteration.simplebanking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

// This class is a place holder you can change the complete implementation
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract  class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	
	@Column(name= "transaction_date" )
	public LocalDateTime transaction_date;
	
	@Column(name= "amount" )
	@Min(value = 1, message = "The balance amount to be added cannot be less than zero.")
	@Max(value = 10000, message = "The balance amount to be added cannot be greater than 10000.")
	public double amount;
	
	@Column(name= "pre_trade_balance" )
	public double preTradeBalance;
	@Column(name= "post_trade_balance" )
	public double postTradeBalance;
	
	@Column(name= "transaction_type", nullable = false)
	public String transaction_type ;

	@Column(name= "approvalCode" )
	public UUID approvalCode;

	

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;


	
	

	

	}

