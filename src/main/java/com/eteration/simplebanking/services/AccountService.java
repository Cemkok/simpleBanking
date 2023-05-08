package com.eteration.simplebanking.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteration.simplebanking.dao.AccountDao;
import com.eteration.simplebanking.dao.TransactionDao;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.BillPaymentTransaction;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.model.dtos.AccountDto;
import com.eteration.simplebanking.utils.dtoResult.ErrorTransactionResponseDto;
import com.eteration.simplebanking.utils.dtoResult.Response;
import com.eteration.simplebanking.utils.dtoResult.TransactionResponseDto;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AccountService implements IAccountService {

	@Autowired
	public AccountDao accountDao;

	@Autowired
	public TransactionDao transactionDao;

	@Override
	public Response saveAccount(AccountDto accountDto) {
		log.debug("AccountService -> saveAccount() method has been trigged ...");
		Account account = new Account();
		try {
			account.setAccountOwner(accountDto.getAccountOwner());
			account.setAccountNumber(accountDto.getAccountNumber());
			account.setCreateTime(LocalDateTime.now());
			accountDao.save(account);
			log.debug("AccountService -> saveAccount() method has completed ...");
			return new TransactionResponseDto("OK", UUID.randomUUID());
		} catch (Exception e) {
			log.error("AccountService -> saveAccount() method has terminated ...  " +e.getMessage());
			return new ErrorTransactionResponseDto(e.getMessage(), "FALSE");

		}

		
	}

	@Override
	public Optional<Account> getAccount(String accountNumber) {
		log.debug("AccountService -> getAccount() method has been trigged ...");
		try {
			Account account = accountDao.findByAccountNumber(accountNumber);
			log.debug("AccountService -> getAccount() method has completed ...");
			return Optional.ofNullable(account);
		} catch (Exception e) {
			log.error("AccountService -> getAccount() method has terminated ...  " +e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Response credit(String accountNumber, double amount) {
		try {
	   
		log.debug("AccountService -> credit() method has been trigged ...");
			
		
		Account account = accountDao.findByAccountNumber(accountNumber);
		account.setBalance(account.getBalance() + amount);

		Transaction transaction = new DepositTransaction(amount);
		transaction.setAccount(account);
		transaction.setTransaction_date(LocalDateTime.now());
		transaction.setAmount(amount);
		transaction.setTransaction_type("DepositTransaction");
		transaction.setApprovalCode(UUID.randomUUID());
		accountDao.save(account);
		transactionDao.save(transaction);
		log.debug("AccountService -> credit() method has completed ...");
		return new TransactionResponseDto("OK", transaction.getApprovalCode());
		} catch (Exception e) {
			log.error("AccountService -> credit() method has terminated ...  " +e.getMessage());
		  return new ErrorTransactionResponseDto(e.getMessage(), "FALSE");
		}
	}

	@Override
	public Response debit(String accountNumber, double amount) {
		log.debug("AccountService -> debit() method has been trigged ...");
		try {
		Account account = accountDao.findByAccountNumber(accountNumber);
		if ((account.getBalance() - amount) < 0) {
			throw new InsufficientBalanceException("Insufficient Balance..");

		}
		account.setBalance(account.getBalance() - amount);

		Transaction transaction = new WithdrawalTransaction(amount);
		transaction.setAccount(account);
		transaction.setTransaction_date(LocalDateTime.now());
		transaction.setAmount(amount);
		transaction.setTransaction_type("WithdrawalTransaction");
		transaction.setApprovalCode(UUID.randomUUID());
		// save metodu ile veriyi güncelliyoruz

		accountDao.save(account);
		transactionDao.save(transaction);
		log.debug("AccountService -> debit() method has completed ...");
		return new TransactionResponseDto("OK", transaction.getApprovalCode());
		} catch (Exception e) {
			log.error("AccountService -> debit() method has terminated ...  " +e.getMessage());
			return new ErrorTransactionResponseDto(e.getMessage(), "FALSE");
		}

	}

	@Override
	public Response billPayment(String accountNumber, double amount, String payee, String billingNumber) {
		log.debug("AccountService -> billPayment() method has been trigged ...");
		try {
			Account account = accountDao.findByAccountNumber(accountNumber);
			if ((account.getBalance() - amount) < 0) {
				throw new InsufficientBalanceException("Insufficient Balance..");

			}
			account.setBalance(account.getBalance() - amount);
			Transaction transaction = new BillPaymentTransaction(payee, billingNumber, amount);
			transaction.setAccount(account);
			transaction.setTransaction_date(LocalDateTime.now());
			transaction.setAmount(amount);
			transaction.setTransaction_type("BillPaymentTransaction");
			transaction.setApprovalCode(UUID.randomUUID());

			accountDao.save(account);
			transactionDao.save(transaction);
			log.debug("AccountService -> billPayment() method has completed ...");
			return new TransactionResponseDto("OK", transaction.getApprovalCode());
		} catch (Exception e) {
			log.error("AccountService -> billPayment() method has terminated ...  " +e.getMessage());
			return new ErrorTransactionResponseDto(e.getMessage(), "FALSE");
		}

	}

}
