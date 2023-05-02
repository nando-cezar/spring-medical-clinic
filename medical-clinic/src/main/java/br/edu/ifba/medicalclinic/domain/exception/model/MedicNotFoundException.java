package br.edu.ifba.medicalclinic.domain.exception.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MedicNotFoundException extends Exception{
    private String name;

    @Override
    public String getMessage() {
        if(name != null) return "Medic '" + name + "' not found";
        return "Medic not found";
    }
}
