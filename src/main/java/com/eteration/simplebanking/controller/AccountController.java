package com.eteration.simplebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.dtos.AccountDto;
import com.eteration.simplebanking.model.dtos.request.BillPaymentRequestDto;
import com.eteration.simplebanking.model.dtos.request.TransactionRequestDto;
import com.eteration.simplebanking.services.IAccountService;

import lombok.extern.slf4j.Slf4j;

// This class is a place holder you can change the complete implementation

@RestController
@RequestMapping("account/v1")
@Slf4j
public class AccountController {
	
	@Autowired
	public IAccountService accountService;
	
	@PostMapping
	public ResponseEntity<?> saveAccount(@RequestBody AccountDto accountDto){
		
		return new ResponseEntity<>(accountService.saveAccount(accountDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/{accountNumber}")
    public ResponseEntity<?>  getAccount(@PathVariable("accountNumber") String accountNumber) {
    	
        return new ResponseEntity<>(accountService.getAccount(accountNumber), HttpStatus.OK);
    }
    
    
    @PostMapping("/credit/{accountNumber}")
    
    public  ResponseEntity<?> credit(@PathVariable("accountNumber") String accountNumber, @RequestBody  TransactionRequestDto transactionRequestDto  ) {
     log.debug("credit method has been trigged ...");
     log.debug("credit method has completed ...");
         return new ResponseEntity<>(accountService.credit(accountNumber,transactionRequestDto.amount), HttpStatus.OK);
    }
    
    
    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<?>  debit(@PathVariable String accountNumber, @RequestBody  TransactionRequestDto transactionRequestDto) {
        log.debug("debit method has been trigged ...");
        log.debug("debit method has completed ...");
    	  return new ResponseEntity<>(accountService.debit(accountNumber,transactionRequestDto.amount), HttpStatus.OK);
	}
    
    @PostMapping("/billPayment/{accountNumber}")
    public ResponseEntity<?>  billPayment(@PathVariable String accountNumber, @RequestBody BillPaymentRequestDto billPaymentRequestDto ) {
    	log.debug("billPayment method has been trigged ...");
        log.debug("billPayment method has completed ...");
    	  return new ResponseEntity<>(accountService.billPayment(accountNumber,billPaymentRequestDto.amount, billPaymentRequestDto.payee, billPaymentRequestDto.billingNumber), HttpStatus.OK);
	}
}