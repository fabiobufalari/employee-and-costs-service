package com.bufalari.employee.convert;

import com.bufalari.employee.dto.AddressDTO;
import com.bufalari.employee.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDTO entityToDTO(AddressEntity addressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(addressEntity.getStreet()); // Using getter
        addressDTO.setCity(addressEntity.getCity());
        addressDTO.setState(addressEntity.getState());
        addressDTO.setPostalCode(addressEntity.getPostalCode());
        addressDTO.setCountry(addressEntity.getCountry());
        return addressDTO;
    }

    public AddressEntity dtoToEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(addressDTO.getStreet()); // Using setter
        addressEntity.setCity(addressDTO.getCity());
        addressEntity.setState(addressDTO.getState());
        addressEntity.setPostalCode(addressDTO.getPostalCode());
        addressEntity.setCountry(addressDTO.getCountry());
        return addressEntity;
    }
}
