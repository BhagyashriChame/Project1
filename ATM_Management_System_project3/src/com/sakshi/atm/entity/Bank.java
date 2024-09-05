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
    
  
    @Column(length=11)
    private String branchCode;
    
    
    @Column(length=20)
    private String branchName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Customer> customer = new ArrayList<>();
    
    }
