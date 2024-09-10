package com.sakshi.atm.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Bank {
    @Id
    
    @Column(length=11)
    private String ifscCode;
    
  // this is simpal 
    @Column(length=11)
    private String branchCote;
    
    
    @Column(length=20)
    private String branchName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Customer> customer = new ArrayList<>();
    
    System.out.println("hi hello");

    }
