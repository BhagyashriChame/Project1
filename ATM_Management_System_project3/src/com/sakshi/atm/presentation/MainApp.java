package com.sakshi.atm.presentation;

import java.util.Scanner;

import com.sakshi.atm.dao.AccountDao;
import com.sakshi.atm.dao.AccountDaoImpl;
import com.sakshi.atm.entity.Card;
import com.sakshi.atm.service.AccountService;
import com.sakshi.atm.service.AccountServiceImpl;

public class MainApp {
    public static void main(String[] args) {

        AccountDao accountDao = new AccountDaoImpl();
        AccountService accountService = new AccountServiceImpl(accountDao);

        App app = new AppImpl(accountService);

        Card card = Login.getLogInDetails();


        if (card != null && card.getAccount() != null) {
            System.out.println();
            System.out.println("=======================================================================================================================================================================\n");
            System.out.println("                                                ======================= Welcome : " + card.getAccount().getCustomer().getCustomerName()+" ======================\n");
            System.out.print("                                              Branch Name : " + card.getAccount().getCustomer().getBank().getBranchName()+
                    "\t\t\t\t Account Number: " + card.getAccount().getAccountNumber());
            System.out.println();
            
        } else {
            System.out.println("                                                Please contact customer support.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println();
            System.out.println("                                                =========================================================================");
            System.out.println("                                                                   What Operation do you want to perform ?");
            System.out.println("                                                =========================================================================\n");
            System.out.println("                                                      1. Deposit\t\t\t\t     3. Mini Statement\n");
            System.out.println("                                                      2. Withdraw\t\t\t\t     4. Check Balance\n");
            System.out.println("                                                      5. Change Pin\t\t\t\t     6. Exit\n");

            System.out.print("\n                                                                   Please enter your choice from above : ");
            String input = scanner.nextLine();
            System.out.println("                                                =========================================================================\n");

            

            if (!input.matches("\\d+")) {
                System.out.println("                                                Please enter a valid choice from the available options.");
                continue;
            }
            

            choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    app.deposit(card);
                    break;
                case 2:
                    app.withdraw(card);
                    break;
                case 3:
                    app.miniStatement(card);
                    break;
                case 4:
                    app.checkBalance(card);
                    break;
                case 5:
                    app.changePin(card);
                    break;
                case 6:
                	app.exit();
                    return;
                default:
                    System.out.println("                                                Please enter a valid choice from the available options.");
                    break;
            }
        }
    }
}