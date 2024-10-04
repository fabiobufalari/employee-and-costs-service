package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String country;
    private String businessIdentificationNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")), // City of the address
            @AttributeOverride(name = "state", column = @Column(name = "address_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))
    }) // Define custom column names for address attributes
    private AddressEntity address;

    // Custom constructor
    public CompanyEntity(String name, String city, String country, String businessIdentificationNumber) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.businessIdentificationNumber = businessIdentificationNumber;
        this.address = new AddressEntity();
    }
}