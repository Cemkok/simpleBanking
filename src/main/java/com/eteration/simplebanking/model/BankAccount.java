package com.eteration.simplebanking.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankAccount {

	public String accountOwner;

	public String accountNumber;

	public double balance;

	public BankAccount(String accountOwner, String accountNumber) {

		this.accountOwner = accountOwner;
		this.accountNumber = accountNumber;
	}

	private List<Transaction> transactions = new ArrayList<>();

	public static void main(String[] args) {

		BankAccount account = new BankAccount("Jim", "123456");
		account.post(new DepositTransaction(1000));
		account.post(new WithdrawalTransaction(-200));
		account.post(new BillPaymentTransaction("Vodafone", "5423345566", 96.50));

		System.out.println("Kalan Bakiyeniz : " + account.getBalance());

	}

	public void post(Transaction transaction) {

		if (transaction.getAmount() >= 0) {

			if (transaction instanceof DepositTransaction) {

				DepositTransaction depositTransaction = (DepositTransaction) transaction;
				this.transactions.add(depositTransaction);
				this.balance = balance + depositTransaction.getAmount();

			} else if (transaction instanceof WithdrawalTransaction) {
				WithdrawalTransaction withdrawalTransaction = (WithdrawalTransaction) transaction;
				this.transactions.add(withdrawalTransaction);
				this.balance = balance - withdrawalTransaction.getAmount();

			} else if (transaction instanceof BillPaymentTransaction) {
				BillPaymentTransaction billPaymentTransaction = (BillPaymentTransaction) transaction;
				this.transactions.add(billPaymentTransaction);
				this.balance = balance - billPaymentTransaction.getAmount();

			} else {
				throw new IllegalArgumentException("Invalid transaction type: " + transaction.getClass());
			}
		} else {
			throw new IllegalArgumentException("The number cannot be negative.");
		}

	}

}
