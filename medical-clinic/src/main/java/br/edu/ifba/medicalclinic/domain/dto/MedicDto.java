package br.edu.ifba.medicalclinic.domain.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.medicalclinic.domain.enums.Specialty;
import br.edu.ifba.medicalclinic.entity.Medic;
import jakarta.validation.constraints.NotNull;

public record MedicDto(
        Long id,
        @NotNull String name,
        @NotNull String email,
        @NotNull String crm,
        @NotNull Specialty specialty,
        AddressDto address
) {
    
    public MedicDto(Medic data){
        this(data.getId(), data.getName(), data.getEmail(), data.getCrm(), data.getSpecialty(), AddressDto.toDto(data.getAddress()));
    }

    public Medic toEntity(){
        return new Medic(name, email, crm, specialty, address.toEntity());
    }

    public static MedicDto toDto(Medic data){
        return new MedicDto(data);
    }

    public static List<MedicDto> toListDto(List<Medic> list){
        return list.stream().map(MedicDto::new).toList();
    }
}
