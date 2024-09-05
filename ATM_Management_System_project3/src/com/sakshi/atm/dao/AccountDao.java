package com.sakshi.atm.dao;

import com.sakshi.atm.entity.Account;
import com.sakshi.atm.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AccountDao {
   
    Account updateAccount(Account account, double newBalance);
    
    String addTransaction(String transactionId, LocalDate date, LocalTime time, String transactionType,Double amount, Account account); 
    
    List<Transaction> getTransactionsForAccount(String accountNumber);
    
	double getBalance(String accountNumber);
	
	double getTotalWithdrawnToday(String accountNumber);
	
}
