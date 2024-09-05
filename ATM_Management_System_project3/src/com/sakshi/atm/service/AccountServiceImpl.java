package com.sakshi.atm.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import com.sakshi.atm.dao.AccountDao;
import com.sakshi.atm.entity.Account;
import com.sakshi.atm.entity.Card;
import com.sakshi.atm.entity.Transaction;

public class AccountServiceImpl implements AccountService {
    
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account updateAccount(Account account, double newBalance) {
        return accountDao.updateAccount(account, newBalance);
    }

    @Override
    public String depositBalance(Card card, double amount) {
        Account account = card.getAccount();
        if (account != null) {
            double currentBalance = account.getBalance();
            if (currentBalance < 0) {
                return "                                                Cannot deposit. Account balance is negative: " + currentBalance;
            }
            
            double newBalance = currentBalance + amount;
            UUID transactionId = UUID.randomUUID();
            String transactionType = "Deposit";
            String result = accountDao.addTransaction(transactionId.toString(), LocalDate.now(), LocalTime.now(), transactionType, amount, account);
            if (result.equals("                                                Transaction added successfully")) {
                account.setBalance(newBalance);
                accountDao.updateAccount(account, newBalance);
                return "                                                Amount deposited successfully. New balance is: " + newBalance + "\n";
            } else {
                return "                                                Failed to deposit amount: " + result;
            }
        }
        return "                                                Invalid card or account details.";
    }

   
    @Override
    public String withdrawBalance(Card card, double amount) {
        Account account = card.getAccount();
        if (account != null) {
            double currentBalance = account.getBalance();
            double dayLimit = 15000.00; // Example day limit, you can change this as needed
            double totalWithdrawnToday = accountDao.getTotalWithdrawnToday(account.getAccountNumber());
            
            if ((totalWithdrawnToday + amount) > dayLimit) {
                return "                                                Withdrawal amount exceeds the daily limit.";
            }

            double newBalance = currentBalance - amount;
            UUID transactionId = UUID.randomUUID();
            String transactionType = "Withdraw";
            String result = accountDao.addTransaction(transactionId.toString(), LocalDate.now(), LocalTime.now(), transactionType, amount, account);
            if (result.equals("                                                Transaction added successfully")) {
                account.setBalance(newBalance);
                accountDao.updateAccount(account, newBalance);
                return "                                                Amount withdrawn successfully. New balance is: " + newBalance +"\n";
            } else {
                return "                                                Failed to withdraw amount: " + result;
            }
        }
        return "                                                Invalid card or account details.";
    }

    @Override
    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        return accountDao.getTransactionsForAccount(accountNumber);
    }

    @Override
    public double getBalance(String accountNumber) {
        return accountDao.getBalance(accountNumber);
    }

    @Override
    public double getTotalWithdrawnToday(String accountNumber) {
        return accountDao.getTotalWithdrawnToday(accountNumber);
    }

    @Override
    public String addTransaction(String transactionId, LocalDate date, LocalTime time, String transactionType, Double amount, Account account) {
        return accountDao.addTransaction(transactionId, date, time, transactionType, amount, account);
    }
}