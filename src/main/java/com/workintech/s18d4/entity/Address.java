package com.workintech.s18d4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore; // Döngüleri engellemek için

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address", schema = "fsweb")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Testlerin uyumu için int/Integer kontrolü önemli

    @Column(name = "street")
    private String street;

    @Column(name = "no")
    private Integer no;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "description") // Bu alan README'ye göre opsiyonel
    private String description;

    @JsonIgnore // JSON döngüsünü engeller
    @OneToOne(mappedBy = "address",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    // Dikkat: Burada CascadeType.REMOVE yok! Çünkü adres silinince müşteri kalsın istiyoruz.
    private Customer customer;
}