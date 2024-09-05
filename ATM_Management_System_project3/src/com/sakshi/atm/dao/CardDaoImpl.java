package com.sakshi.atm.dao;

import javax.persistence.EntityManager;

import com.sakshi.atm.entity.Card;

public class CardDaoImpl implements CardDao {
    private EntityManager entityManager;

    public CardDaoImpl() {
        entityManager = MyConnection.getEntityManagerObject();
    }

    @Override
    public Card getCardByCardNumber(String cardNumber) {
        return entityManager.find(Card.class, cardNumber);
    }
    @Override
    public Card getCardByCardNumberAndPin(String cardNumber, String pin) {
        // Retrieve the card based on the card number
        Card card = entityManager.find(Card.class, cardNumber);
        
        // Check if the retrieved card has the correct PIN
        if (card != null && card.getCardPin().equals(pin)) {
            return card;
        } else {
            return null;
        }
    }


    @Override
    public String updateCardStatus(Card card) {
        Card card1 = entityManager.find(Card.class, card.getCardNumber());
        card1.setCardStatus("Deactive");
        entityManager.getTransaction().begin();
        entityManager.persist(card1);
        entityManager.getTransaction().commit();
        return "                                                Updated";
    }

    @Override
    public String updateCardPin(Card card) {
        entityManager.getTransaction().begin();
        Card cardToUpdate = entityManager.find(Card.class, card.getCardNumber());
        cardToUpdate.setCardPin(card.getCardPin());
        entityManager.getTransaction().commit();
        return "                                                PIN updated successfully.";
    }
}
