package com.sakshi.atm.presentation;

import java.util.Scanner;

import com.sakshi.atm.entity.Card;
import com.sakshi.atm.service.CardService;
import com.sakshi.atm.service.CardServiceImpl;
import com.sakshi.atm.validation.Validation;

public class Login {
    private static Scanner scanner = new Scanner(System.in);
    private static String cardNumber;
    private static String pin;
    private static Card card;
    private static CardService cardService = new CardServiceImpl();
    private static Integer countPin = 0;

    public static Card getLogInDetails() {
        System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*\n");
        System.out.println("                                                \033[1m========================= WELCOME TO ATM SYSTEM =======================\033[0m\n");
        System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*\n");
        
        while (true) {
        	System.out.print("                                                Enter Card Number: ");
            cardNumber = scanner.nextLine();

            if (!Validation.checkCardNo(cardNumber)) {
                continue;
            }

            card = cardService.getCardByCardNumber(cardNumber);

            if (card == null) {
            	System.out.println("                                                Card not found. Please try again.");
                continue;
            }

            if (!Validation.checkCardStatus(card.getCardStatus())) {
            	System.out.println("                                                Your card is blocked.\n");
                return null; 
            }

            break;
        }

        while (true) {
        	System.out.print("                                                Enter PIN: ");
            pin = scanner.nextLine();

            if (validatePin(pin)) {
                break;
            }

            countPin++;
            if (countPin >= 3) {
            	System.out.println("                                                Card is blocked.");
                cardService.updateCardStatus(card);
                return null; 
            }
            
        }

        return card;
    }

    private static boolean validatePin(String pin) {
        // Check if the PIN is exactly four digits and contains only digits
        if (!pin.matches("\\d{4}")) {
            System.out.println("                                                PIN should contain only digits and be exactly four digits long. Please try again.");
            return false;
        }

        // Retrieve the card based on the card number and PIN
        Card validatedCard = cardService.getCardByCardNumberAndPin(cardNumber, pin);
        if (validatedCard == null) {
            System.out.println("                                                PIN change failed. Please verify your PIN and try again.");
            return false;
        }

        return true;
    }



}
