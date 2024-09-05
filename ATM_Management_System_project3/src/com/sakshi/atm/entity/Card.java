package com.sakshi.atm.entity;
import javax.persistence.*;

import com.sakshi.atm.dao.CardDao;
import com.sakshi.atm.dao.CardDaoImpl;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Card {
    @Id
    @Column(length=16)
    @Pattern(regexp = "\\d{16}", message = "Card number must be a 16-digit number")
    private String cardNumber;
    @Column(length=15)
    private String cardType;
    @Future(message = "Expiry date must be in the future")
    private Date expiryDate;
    private Date issueDate;
    @Column(length=4)
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits")
    private Integer cvvNumber;
    @Column(length=4)
    @Pattern(regexp = "\\d{4}", message = "Card PIN must be a 4-digit number")
    private String cardPin;
    @Column(length=10)
    private String cardStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountNumber")
    private Account account;

    
}



