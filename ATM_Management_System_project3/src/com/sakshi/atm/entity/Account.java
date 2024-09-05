package com.sakshi.atm.entity;
import javax.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class Account {
    @Id    
    @Column(length=17)
    @Pattern(regexp="\\d+", message="Account number must contain only digits")

    private String accountNumber;
    @Column(length=20)
    private String accountType;
    @Min(value = 0, message = "Balance must be non-negative")
    private double balance;
    @Column(length=9)
    private String status;
    @NotNull(message = "Opening date cannot be null")
    @PastOrPresent(message = "Opening date must be in the past or present")
   
    private Date openingDate;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> card = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transaction = new ArrayList<>();
    
}
