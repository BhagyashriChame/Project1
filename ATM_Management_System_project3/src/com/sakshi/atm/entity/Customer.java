package com.sakshi.atm.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id
    @Column(length=10)
    private String customerId;

    @Column(length=20)
    @Pattern(regexp="[a-zA-Z ]*", message="Customer name must contain only letters and spaces")
    private String customerName;

    @Column(length=10)
    @Pattern(regexp="[0-9]+", message="Contact must contain only numeric characters")
    @NotNull(message="Contact cannot be null")
    private String contact;

    @Column(length=50)
    private String address;

    private Date dateOfBirth;

    @Column(length=12)
    @Pattern(regexp="[0-9]{12}", message="Aadhaar number must be exactly 12 decimal digits")
    private String adhaarNo;

    @Column(length=11)
    @Pattern(regexp="^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message="PAN number must be in the format ABCDE1234F")
    private String panNo;

    @Column(length=7)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "ifscCode")
    private Bank bank;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> account = new ArrayList<>();
}
