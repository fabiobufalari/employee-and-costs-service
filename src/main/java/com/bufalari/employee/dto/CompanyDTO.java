package com.bufalari.employee.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String businessIdentificationNumber; // Canadian equivalent of CNPJ
    private AddressDTO address;
    private List<ContactDTO> contacts;
    private String mainActivity;
    private LocalDate foundationDate;
    private ManagerResponsibleDTO manager;
    private ManagerResponsibleDTO responsible;
    private String googleMapsLink;
}
