package com.bufalari.employee.convert;

import com.bufalari.employee.dto.CompanyDTO;
import com.bufalari.employee.entity.CompanyEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter {

    public CompanyDTO entityToDTO(CompanyEntity companyEntity) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(companyEntity.getId());
        companyDTO.setName(companyEntity.getName());
        companyDTO.setBusinessIdentificationNumber(companyEntity.getBusinessIdentificationNumber());
        companyDTO.setAddress(new AddressConverter().entityToDTO(companyEntity.getAddress()));
        // Outros atributos omitidos para brevidade
        return companyDTO;
    }

    public CompanyEntity dtoToEntity(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        AddressConverter addressConverter = new AddressConverter();

        companyEntity.setId(companyDTO.getId());
        companyEntity.setName(companyDTO.getName());
        companyEntity.setBusinessIdentificationNumber(companyDTO.getBusinessIdentificationNumber());
        companyEntity.setAddress(new AddressConverter().dtoToEntity(companyDTO.getAddress()));
        // Outros atributos omitidos para brevidade
        return companyEntity;
    }
}
