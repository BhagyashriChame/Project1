package com.sakshi.atm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.sakshi.atm.entity.Account;
import com.sakshi.atm.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class AccountDaoImpl implements AccountDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public AccountDaoImpl() {
        super();
        entityManager = MyConnection.getEntityManagerObject();
    }
    
    @Override
    public double getBalance(String accountNumber) {
        Query query = entityManager.createQuery("SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber");
        query.setParameter("accountNumber", accountNumber);
        List<Double> balances = query.getResultList();
        if (!balances.isEmpty()) {
            return balances.get(0);
        } else {
            return 0; 
        }
    } 

    @Override
    public Account updateAccount(Account account, double newBalance) {
        entityTransaction = entityManager.getTransaction();
        if (entityTransaction != null) {
            entityTransaction.begin();
            account.setBalance(newBalance);
            entityManager.merge(account);
            entityTransaction.commit();
            return account;
        }
        return null;
    }
  @Override
        public List<Transaction> getTransactionsForAccount(String accountNumber) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber");
        query.setParameter("accountNumber", accountNumber);
        return query.getResultList();
    }

    @Override
    public double getTotalWithdrawnToday(String accountNumber) {
        LocalDate today = LocalDate.now();
        Query query = entityManager.createQuery("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.accountNumber = :accountNumber AND t.transactionType = 'Withdraw' AND t.date = :date");
        query.setParameter("accountNumber", accountNumber);
        query.setParameter("date", today);
        Double totalWithdrawnAmount = (Double) query.getSingleResult();
        return totalWithdrawnAmount != null ? totalWithdrawnAmount : 0.0;
    }

	
	@Override
	public String addTransaction(String transactionId, LocalDate date, LocalTime time, String transactionType,
			Double amount, Account account) {
		Transaction transaction = new Transaction(transactionId, date, time, transactionType, amount, account);
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(transaction);
            entityTransaction.commit();
            return "                                                Transaction added successfully";
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            return "                                                Failed to add transaction: " + e.getMessage();
        }
	}
}
