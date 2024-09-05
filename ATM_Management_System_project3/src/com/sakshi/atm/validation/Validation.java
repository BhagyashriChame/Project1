package com.sakshi.atm.validation;

public class Validation {

    public static Boolean checkCardNo(String cardNo) {
        if (cardNo.length() != 16) {
            System.out.println("                                                Card number must be exactly 16 digits. Please try again.");
            return false;
        }

        if (!cardNo.matches("\\d{16}")) {
            System.out.println("                                                Card number can only contain digits. Please try again.");
            return false;
        }

        return true;
    }

    public static Boolean checkPinNo(String pinNo) {
        if (pinNo.matches("\\d{4}")) {
            for (int i = 0; i < pinNo.length(); i++) {
                char currentChar = pinNo.charAt(i);
                for (int j = i + 1; j < pinNo.length(); j++) {
                    if (currentChar == pinNo.charAt(j)) {
                        return false;
                    }
                }
            }
            return true; // No duplicate digits found
        } else {
            return false; // Invalid PIN format
        }
    }

    public static Boolean isValidPin(String pin) {
        return pin.matches("\\d{4}");
    }

    public static Boolean checkCardStatus(String cardStatus) {
        return cardStatus.equals("Active");
    }

    public static boolean checkAmount(double amount) {
        // Check if the amount is a positive number
        if (amount <= 0) {
            return false; // Amount cannot be zero or negative
        }

        // Check if the amount is a valid double value
        return String.valueOf(amount).matches("^[0-9]+(\\.[0-9]+)?$"); // Check if the amount is in the correct numerical format
    }

    public static Boolean checkAmountLimit1(Double amount) {
        return amount <= 10000; // Check if the amount is less than or equal to 10000
    }

    public static Boolean checkAmountLimit(Double amount) {
        return amount <= 15000; // Check if the amount is less than or equal to 15000
    }

    public static boolean checkDayLimit(double amount, double dayLimit) {
        return amount <= dayLimit;
    }

    public static boolean checkMultipleOf100(double amount) {
        return amount % 100 == 0;
    }
    public static Boolean checkAmountLimit(Double amount, String transactionType) {
        if ("deposit".equalsIgnoreCase(transactionType)) {
            // Check if the deposit amount is less than or equal to 500000
            return amount <= 500000;
        } else if ("withdraw".equalsIgnoreCase(transactionType)) {
            // Check if the withdrawal amount is less than or equal to 20000
            return amount <= 20000;
        } else {
            return false; // Invalid transaction type
        }
    }
}
