package com.sakshi.atm.presentation;

import com.sakshi.atm.entity.Card;

public interface App {

	void deposit(Card card);
    void withdraw(Card card);
	void checkBalance(Card card);
	void miniStatement(Card card);
    void changePin(Card card);
	void exit();
	
}
