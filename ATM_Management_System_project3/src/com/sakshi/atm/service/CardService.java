package com.sakshi.atm.service;

import com.sakshi.atm.entity.Card;

public interface CardService {
    
    Card getCardByCardNumberAndPin(String cardNumber, String pin);

    Card getCardByCardNumber(String cardNumber);
    
    
    String updateCardStatus(Card card);
    
     String updateCardPin(Card card);

}