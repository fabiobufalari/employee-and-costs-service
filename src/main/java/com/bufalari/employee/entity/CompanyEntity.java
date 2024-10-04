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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "company") // Add CascadeType.PERSIST
    @MapsId
    private AddressEntity address;

    // Construtor customizado
    public CompanyEntity(String name, String city, String country, String businessIdentificationNumber) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.businessIdentificationNumber = businessIdentificationNumber;

        this.address = new AddressEntity();
        // Define o ID do endereço aqui:
        this.address.setId(this.id); //  <- Adicione essa linha
    }
}