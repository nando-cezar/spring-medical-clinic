package br.edu.ifba.medicalclinic.domain.exception.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ContentNotAllowedException extends Exception{
    List<ObjectError> errors;
    public List<ObjectError> getErrors() {
        return errors;
    }

}
