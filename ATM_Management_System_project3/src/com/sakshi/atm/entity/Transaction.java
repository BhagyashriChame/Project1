package com.sakshi.atm.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "time")
    private LocalTime time;
    
    @Column(name = "transaction_type")
    private String transactionType;
    
    @Column(name = "amount")
    private Double amount;
    
    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;
    
    public Transaction() {
        super();
        this.transactionId = UUID.randomUUID().toString(); 
    }

    public Transaction(String transactionId, LocalDate date, LocalTime time, String transactionType, Double amount, Account account) {
        super();
        this.transactionId = transactionId;
        this.date = date;
        this.time = time;
        this.transactionType = transactionType;
        this.amount = amount;
        this.account = account;
    }

}