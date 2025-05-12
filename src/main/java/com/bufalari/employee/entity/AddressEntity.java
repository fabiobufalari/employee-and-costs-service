package com.bufalari.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEntity {

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number", length = 50)
    private String number;

    @Column(name = "address_city")
    private String city;

    @Column(name = "address_province") // <<< ALTERADO DE state para province
    private String province;

    @Column(name = "address_postal_code")
    private String postalCode;

    @Column(name = "address_country")
    private String country;
}