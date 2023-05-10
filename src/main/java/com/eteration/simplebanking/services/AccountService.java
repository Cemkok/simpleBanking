package com.eteration.simplebanking.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteration.simplebanking.dao.AccountDao;
import com.eteration.simplebanking.dao.TransactionDao;
import com.eteration.simplebanking.exception.customException.AccountNotFoundException;
import com.eteration.simplebanking.exception.customException.CustomerAlreadyExistException;
import com.eteration.simplebanking.exception.customException.InsufficientBalanceException;
import com.eteration.simplebanking.exception.customException.NegativeValueEntryException;
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

/**
 * 
 * @author cem kök
 *
 */
@Service
@Slf4j
public class AccountService implements IAccountService {
	public double preTradeBalance;

	@Autowired
	public AccountDao accountDao;

	@Autowired
	public TransactionDao transactionDao;

	/**
	 * This method adds a new account.
	 */
	@Override
	public Response saveAccount(AccountDto accountDto) throws CustomerAlreadyExistException {
		log.debug("The saveAccount() method has been trigged ...");

		Account account = new Account(accountDto.getAccountOwner(), accountDto.getAccountNumber());
		if (accountDao.existsByAccountNumber(accountDto.getAccountNumber())) {
			throw new CustomerAlreadyExistException();
		}

		try {

			accountDao.save(account);
			log.debug("The saveAccount() method has completed ...");
			return new TransactionResponseDto("OK", UUID.randomUUID());
		} catch (Exception e) {
			log.error("<!> saveAccount() method has terminated ...  " + e.getMessage());
			return new ErrorTransactionResponseDto(e.getMessage(), "FALSE");

		}

	}

	/**
	 * This method returns registered accounts.
	 */
	@Override
	public Account getAccount(String accountNumber) throws AccountNotFoundException {
		log.debug("The getAccount() method has been trigged ...");

		Account account = accountDao.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException();
		}
		log.debug("The getAccount() method has completed ...");
		return account;

	}

	/**
	 * This method loads balance.
	 */
	@Override
	public Response credit(String accountNumber, double amount) throws AccountNotFoundException {

		log.debug("The credit() method has been trigged ...");

		Account account = accountDao.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException();
		}
		this.preTradeBalance = account.getBalance();
		account.setBalance(account.getBalance() + amount);
		Transaction transaction = transactionCreator(new DepositTransaction(amount), account, preTradeBalance);

		accountDao.save(account);
		transactionDao.save(transaction);
		log.debug("The credit() method has completed ...");
		return new TransactionResponseDto("OK", transaction.getApprovalCode());

	}

	/**
	 * This method withdraws money.
	 */
	@Override
	public Response debit(String accountNumber, double amount)
			throws InsufficientBalanceException, NegativeValueEntryException, AccountNotFoundException {
		log.debug("The debit() method has been trigged ...");

		Account account = accountDao.findByAccountNumber(accountNumber);
		this.preTradeBalance = account.getBalance();
		if (account == null) {
			throw new AccountNotFoundException();
		}
		if ((account.getBalance() - amount) < 0) {
			throw new InsufficientBalanceException();
		}
		account.setBalance(account.getBalance() - amount);
		Transaction transaction = transactionCreator(new WithdrawalTransaction(amount), account, preTradeBalance);

		// save metodu ile veriyi güncelliyoruz

		accountDao.save(account);
		transactionDao.save(transaction);
		log.debug("The debit() method has completed ...");
		return new TransactionResponseDto("OK", transaction.getApprovalCode());

	}
	
	
    /**
     * This method is for paying bills, the balance is deducted.
     */
	@Override
	public Response billPayment(String accountNumber, double amount, String payee, String billingNumber)
			throws InsufficientBalanceException, AccountNotFoundException {
		log.debug("The billPayment() method has been trigged ...");

		Account account = accountDao.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException();
		}
		this.preTradeBalance = account.getBalance();
		if ((account.getBalance() - amount) < 0) {
			throw new InsufficientBalanceException();
		}
		account.setBalance(account.getBalance() - amount);
		Transaction transaction = transactionCreator(new BillPaymentTransaction(payee, billingNumber, amount), account,
				preTradeBalance);

		accountDao.save(account);
		transactionDao.save(transaction);
		log.debug("The billPayment() method has completed ...");
		return new TransactionResponseDto("OK", transaction.getApprovalCode());

	}
  /**
   * This method was created to avoid code duplication.
   * @param transaction
   * @param account
   * @param preTradeBalance
   * @return
   */
	public Transaction transactionCreator(Transaction transaction, Account account, double preTradeBalance) {
		log.info("The transactionCreator method has been invoke.");
		transaction.setAccount(account);
		transaction.setPreTradeBalance(preTradeBalance);
		transaction.setPostTradeBalance(account.getBalance());
		log.info("The transactionCreator method has  completed.");
		return transaction;
	}

}
