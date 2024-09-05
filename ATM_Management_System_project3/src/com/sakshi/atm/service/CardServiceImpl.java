package com.sakshi.atm.service;

import com.sakshi.atm.dao.CardDao;
import com.sakshi.atm.dao.CardDaoImpl;
import com.sakshi.atm.entity.Card;

public class CardServiceImpl implements CardService {
    
    CardDao cardDao = new CardDaoImpl();

    @Override
    public String updateCardStatus(Card card) {
        
        return cardDao.updateCardStatus(card);
    }
    @Override
    public Card getCardByCardNumber(String cardNumber) {
        return cardDao.getCardByCardNumber(cardNumber);
    }


    @Override
    public String updateCardPin(Card card) {
        return cardDao.updateCardPin(card);
    }
    @Override
    public Card getCardByCardNumberAndPin(String cardNumber, String pin) {
        return cardDao.getCardByCardNumberAndPin(cardNumber, pin);
    }

}
