package br.edu.ifba.medicalclinic.domain.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    List<String> errors;
}
