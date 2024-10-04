package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity// AddressEntity is now embeddable
public class AddressEntity {

    @Id
    private Long id;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @OneToOne
    @JoinColumn(name = "id")
    private CompanyEntity company;

}