package com.sakshi.atm.service;

import com.sakshi.atm.entity.Account;
import com.sakshi.atm.entity.Card;
import com.sakshi.atm.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AccountService {
    Account updateAccount(Account account, double newBalance);
    String addTransaction(String transactionId, LocalDate date, LocalTime time, String transactionType, Double amount, Account account); 
    String depositBalance(Card card, double amount);
    String withdrawBalance(Card card, double amount);
    List<Transaction> getTransactionsForAccount(String accountNumber);
    double getBalance(String accountNumber);
    double getTotalWithdrawnToday(String accountNumber);
}