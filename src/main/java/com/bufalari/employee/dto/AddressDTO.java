package com.bufalari.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotNull(message = "Street name is required")
    private String street;

    @NotNull(message = "Street number is required")
    private String number;

    private String complement;
    private String neighbourhood;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Province is required")
    private String province;

    @NotNull(message = "Postal code is required")
    private String postalCode;

    @NotNull(message = "State is required")
    private String state;

    @NotNull(message = "Country is required")
    private String country;

    private String googleMapsLink;
}
