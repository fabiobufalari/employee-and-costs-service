package com.bufalari.employee.convert;

import com.bufalari.employee.dto.AddressDTO;
import com.bufalari.employee.entity.AddressEntity; // Alterado para AddressEntity
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDTO entityToDTO(AddressEntity addressEntity) {
        if (addressEntity == null) return null;
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(addressEntity.getStreet());
        addressDTO.setNumber(addressEntity.getNumber()); // Adicionado
        addressDTO.setCity(addressEntity.getCity());
        addressDTO.setProvince(addressEntity.getProvince()); // Consistente
        addressDTO.setPostalCode(addressEntity.getPostalCode());
        addressDTO.setCountry(addressEntity.getCountry());
        return addressDTO;
    }

    public AddressEntity dtoToEntity(AddressDTO addressDTO) {
        if (addressDTO == null) return null;
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(addressDTO.getStreet());
        addressEntity.setNumber(addressDTO.getNumber()); // Adicionado
        addressEntity.setCity(addressDTO.getCity());
        addressEntity.setProvince(addressDTO.getProvince()); // Consistente
        addressEntity.setPostalCode(addressDTO.getPostalCode());
        addressEntity.setCountry(addressDTO.getCountry());
        return addressEntity;
    }
}