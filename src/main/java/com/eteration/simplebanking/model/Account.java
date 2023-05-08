package com.eteration.simplebanking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;

import com.eteration.simplebanking.dao.AccountDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// This class is a place holder you can change the complete implementation
/**
 * 
 * @author cem kök
 *
 */

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	@JsonIgnore
	@Transient
	@Autowired
	public AccountDao accountDao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "accountNumber", length = 100, nullable = false, unique = true)
	public String accountNumber;

	@Column(name = "accountOwner", length = 100, nullable = false)
	public String accountOwner;

	@Column(name = "balance", nullable = false)
	@PositiveOrZero
	public double balance;

	public LocalDateTime createTime;

	@JsonManagedReference
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transactions = new ArrayList<>();

	public Account(String accountOwner, String accountNumber) {

		this.accountOwner = accountOwner;
		this.accountNumber = accountNumber;
	}

	public Account post(Transaction transaction) {

		if (transaction instanceof DepositTransaction) {
			DepositTransaction depositTransaction = (DepositTransaction) transaction;

			this.setBalance(this.getBalance() + depositTransaction.amount);

		} else if (transaction instanceof WithdrawalTransaction) {
			WithdrawalTransaction withdrawalTransaction = (WithdrawalTransaction) transaction;
			this.setBalance(this.getBalance() - withdrawalTransaction.amount);

		} else if (transaction instanceof BillPaymentTransaction) {
			BillPaymentTransaction billPaymentTransaction = (BillPaymentTransaction) transaction;
			this.setBalance(this.getBalance() - billPaymentTransaction.amount);

		} else {
			throw new IllegalArgumentException("Invalid transaction type: " + transaction.getClass());
		}

		return this;
	}

	public static void main(String[] args) {
		Account account = new Account("Jim", "12345");
		account.post(new DepositTransaction(1000));
		account.post(new WithdrawalTransaction(200));
		account.post(new BillPaymentTransaction("Vodafone", "5423345566", 96.50));
		System.out.println(account.getBalance());

	}

}
