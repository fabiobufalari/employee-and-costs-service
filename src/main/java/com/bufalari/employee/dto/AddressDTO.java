package com.bufalari.employee.dto;

import jakarta.validation.constraints.NotBlank; // Usar @NotBlank para Strings
import jakarta.validation.constraints.NotNull; // Mantido para referência futura
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "{address.street.required}") // NotBlank é mais apropriado para String
    private String street;

    // O campo 'number' estava faltando no DTO, mas presente no Converter/Entity original. Adicionando.
    @NotBlank(message = "{address.number.required}")
    private String number; // <<< ADICIONADO

    // Estes campos não estavam no DTO original, mas estavam no converter/entity de company. Removendo por consistência com o DTO atual.
    // private String complement;
    // private String neighbourhood;

    @NotBlank(message = "{address.city.required}")
    private String city;

    @NotBlank(message = "{address.province.required}") // Renomeado de 'state' para 'province' para consistência com company
    private String province;

    @NotBlank(message = "{address.postalCode.required}")
    private String postalCode;

    @NotBlank(message = "{address.country.required}")
    private String country;

    // O campo googleMapsLink não estava no DTO, adicionar se necessário.
    // private String googleMapsLink;

    // O campo 'state' foi removido para usar 'province' consistentemente.
    // @NotNull(message = "State is required")
    // private String state;
}