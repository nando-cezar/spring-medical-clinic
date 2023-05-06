package br.edu.ifba.medicalclinic.domain.dto;

import java.util.List;

import br.edu.ifba.medicalclinic.domain.enums.Specialty;
import br.edu.ifba.medicalclinic.entity.Medic;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicDto(
        Long id,
        @NotNull String name,
        @NotNull String email,
        @NotNull String telephone,
        @NotNull
        @Size(
                max = 6,
                message = "Maximum number of characters has been exceeded"
        )
        String crm,
        @NotNull Specialty specialty,
        AddressDto address
) {
    
    public MedicDto(Medic data){
        this(data.getId(), data.getName(), data.getEmail(), data.getTelephone(), data.getCrm(), data.getSpecialty(), AddressDto.toDto(data.getAddress()));
    }

    public Medic toEntity(){
        return new Medic(name, email, telephone, crm, specialty, address.toEntity());
    }

    public static MedicDto toDto(Medic data){
        return new MedicDto(data);
    }

    public static List<MedicDto> toListDto(List<Medic> list){
        return list.stream().map(MedicDto::new).toList();
    }
}
