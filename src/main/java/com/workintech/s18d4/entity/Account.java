package com.workintech.s18d4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // Bunu ekle
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account", schema = "fsweb")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "money_amount")
    private Double moneyAmount;

    @JsonBackReference // Döngüyü kırmak için bu anotasyonu ekledik
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
