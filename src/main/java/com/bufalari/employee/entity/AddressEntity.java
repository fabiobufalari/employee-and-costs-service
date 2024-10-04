package com.bufalari.employee.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // AddressEntity is now an embeddable type
public class AddressEntity {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}