package com.sakshi.atm.dao;

import com.sakshi.atm.entity.Card;

public interface CardDao {
	
	
	String updateCardStatus(Card card);
	
	String updateCardPin(Card card);

	Card getCardByCardNumberAndPin(String cardNumber, String pin);

	Card getCardByCardNumber(String cardNumber);
	
	
}