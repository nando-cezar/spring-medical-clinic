package br.edu.ifba.medicalclinic.domain.dto;

import br.edu.ifba.medicalclinic.entity.Address;
import jakarta.validation.constraints.NotNull;

public record AddressDto(String fullAddress) {
    
    public AddressDto(Address data){
        this(data.getFullAddress());
    }
    public Address toEntity(){
        return new Address(fullAddress);
    }

    public static AddressDto toDto(Address data){
        return new AddressDto(data);
    }
}
