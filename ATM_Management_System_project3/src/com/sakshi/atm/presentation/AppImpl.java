package com.sakshi.atm.presentation;

import java.util.List;
import java.util.Scanner;

import com.sakshi.atm.dao.CardDao;
import com.sakshi.atm.dao.CardDaoImpl;
import com.sakshi.atm.entity.Card;
import com.sakshi.atm.entity.Transaction;
import com.sakshi.atm.service.AccountService;
import com.sakshi.atm.validation.Validation;

public class AppImpl implements App {
    private final AccountService accountService;
    private final Scanner scanner = new Scanner(System.in);
    private final Double DAY_LIMIT = 15000.00;
    private CardDao cardDao;

    public AppImpl(AccountService accountService) {
        this.accountService = accountService;
        this.cardDao = new CardDaoImpl();
    }

    @Override
    public void deposit(Card card) {
        System.out.print("                                                Enter the amount to deposit: ");
        double amount = scanner.nextDouble();

        if (!Validation.checkAmount(amount)) {
            System.err.println("                                                Invalid amount. Please enter a valid positive number.");
            return;
        }

        if (!Validation.checkAmountLimit(amount, "deposit")) {
            System.err.println("                                                You can deposit only up to 500000 at a time.");
            return;
        }

        String result = accountService.depositBalance(card, amount);
        System.out.println(result);

        // Ask if the user wants to generate a receipt
        System.out.print("                                                Do you want to generate a receipt? (Y/N) : ");
        String generateReceipt = scanner.next();
        
        if ("Y".equalsIgnoreCase(generateReceipt)) {
            generateReceipt(card.getAccount().getAccountNumber(), amount);
        }
    }


    @Override
    public void withdraw(Card card) {
        System.out.print("                                                Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (!Validation.checkAmount(amount)) {
            System.err.println("                                                Invalid amount. Please enter a valid positive number.");
            return;
        }

        // Check if the amount is a multiple of 100
        if (!Validation.checkMultipleOf100(amount)) {
            System.err.println("                                                Withdrawal amount must be a multiple of 100.");
            return;
        }

        // Check if the total amount withdrawn in a day exceeds the day limit
        double totalWithdrawnToday = accountService.getTotalWithdrawnToday(card.getAccount().getAccountNumber());
        if (!Validation.checkDayLimit(totalWithdrawnToday + amount, DAY_LIMIT)) {
            System.err.println("                                                You can withdraw only up to 15,000 amount in one day.");
            return;
        }

        // Check if the amount requested is greater than the balance
        double balance = accountService.getBalance(card.getAccount().getAccountNumber());
        if (amount > balance) {
            System.err.println("                                                Insufficient funds. Withdrawal amount exceeds account balance.");
            return;
        }

        String result = accountService.withdrawBalance(card, amount);
        System.out.println(result);

        // Ask if the user wants to generate a receipt1
        System.out.print("                                                Do you want to generate a receipt? (Y/N) : ");
        String generateReceipt = scanner.next();
        if ("Y".equalsIgnoreCase(generateReceipt)) {
            generateReceipt(card.getAccount().getAccountNumber(), amount);
        }
    }


    private void generateReceipt(String accountNumber, double amount) {
        List<Transaction> transactions = accountService.getTransactionsForAccount(accountNumber);
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        System.out.println("\n                                               ==========================================================================");
        System.out.println("                                                              Mini Statement for Account Number: " + accountNumber);
        System.out.println("                                               ==========================================================================\n");
        System.out.println("                                                Transaction ID: " + lastTransaction.getTransactionId());
        System.out.println("                                                Date: " + lastTransaction.getDate());
        System.out.println("                                                Time: " + lastTransaction.getTime());
        System.out.println("                                                Transaction Type: " + lastTransaction.getTransactionType());
        System.out.println("                                                Amount: " + amount);
    }

    @Override
    public void miniStatement(Card card) {
        List<Transaction> transactions = accountService.getTransactionsForAccount(card.getAccount().getAccountNumber());
        if (transactions.isEmpty()) {
            System.out.println("                                                No transactions found for this account.");
        } else {
        	 System.out.println("\n                                                ==========================================================================");
            System.out.println("                                                                Mini Statement for Account Number: " + card.getAccount().getAccountNumber());
            System.out.println("                                                ==========================================================================\n");
            for (Transaction transaction : transactions) {
                System.out.println("                                                Transaction ID: " + transaction.getTransactionId());
                System.out.println("                                                Date: " + transaction.getDate());
                System.out.println("                                                Time: " + transaction.getTime());
                System.out.println("                                                Transaction Type: " + transaction.getTransactionType());
                System.out.println("                                                Amount: " + transaction.getAmount()); // Add this line
                System.out.println("                                                ==============================================================");
            }
        }
    }

    @Override
    public void checkBalance(Card card) {
        double balance = accountService.getBalance(card.getAccount().getAccountNumber());
        System.out.println("                                                Account balance is: " + balance);
    }

    @Override
    public void changePin(Card card) {
        System.out.print("                                                Enter current PIN: ");
        String currentPin = scanner.next();

        if (!currentPin.equals(card.getCardPin())) {
            System.out.println("                                                Incorrect current PIN. PIN change failed.");
            return;
        }

        while (true) {
            System.out.print("                                                Enter new PIN: ");
            String newPin = scanner.next();

            // Check if newPin contains non-digit characters
            if (!newPin.matches("\\d+")) {
                System.out.println("                                                New PIN should contain only digits.");
                continue;
            }

            // Check if newPin has exactly four digits
            if (newPin.length() != 4) {
                System.out.println("                                                New PIN should be exactly four digits.");
                continue;
            }

            // Check for duplicate digits
            if (!Validation.checkPinNo(newPin)) {
                System.out.println("                                                New PIN cannot contain duplicate digits.");
                continue;
            }

            System.out.print("                                                Confirm new PIN: ");
            String confirmPin = scanner.next();

            if (!newPin.equals(confirmPin)) {
                System.out.println("                                                PINs do not match.");
                continue;
            }

            // Update the card PIN in the database
            Card updatedCard = cardDao.getCardByCardNumberAndPin(card.getCardNumber(), currentPin);
            if (updatedCard == null) {
                System.out.println("                                                Invalid PIN. PIN change failed.");
                return;
            }

            updatedCard.setCardPin(newPin);
            String updateResult = cardDao.updateCardPin(updatedCard);
            System.out.println(updateResult);

            break;
        }
    }

    @Override
    public void exit() {
    	 System.out.println("\n                                                \033[1m*^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^*^*\033[0m");
    	    System.out.println("                                                              \033[1mExiting ATM. Thank you for using our services.\033[0m");
    	    System.out.println("                                                \033[1m*^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^**^*^*^*^*\033[0m");
    	    System.exit(0);
    }
}
