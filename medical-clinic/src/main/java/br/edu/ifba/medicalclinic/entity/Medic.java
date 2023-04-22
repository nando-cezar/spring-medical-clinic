package br.edu.ifba.medicalclinic.entity;

import br.edu.ifba.medicalclinic.domain.enums.Specialty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "medics")
public class Medic{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @Embedded
    private Address address;

    public Medic(String name, String email, String crm, Specialty specialty, Address address) {
        this.name = name;
        this.email = email;
        this.crm = crm;
        this.specialty = specialty;
        this.address = address;
    }
    
}