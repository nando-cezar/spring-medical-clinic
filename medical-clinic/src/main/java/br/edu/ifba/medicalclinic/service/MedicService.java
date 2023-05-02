package br.edu.ifba.medicalclinic.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.medicalclinic.domain.exception.handler.GlobalExceptionHandler;
import br.edu.ifba.medicalclinic.domain.exception.model.MedicNotFoundException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.medicalclinic.domain.dto.MedicDto;
import br.edu.ifba.medicalclinic.repository.MedicRepository;

@Service
public class MedicService {
    
    @Autowired
    private MedicRepository repository;

    public MedicDto save(MedicDto data){
        var entity = data.toEntity();
        return MedicDto.toDto(repository.save(entity));
    }

    @SneakyThrows
    public List<MedicDto> find(String name, Pageable pageable) {
        if(name == null){
            var data = repository.findAll(pageable).toList();
            if(data.isEmpty()) throw new MedicNotFoundException();
            return MedicDto.toListDto(data);
        }
        var data = repository.findByNameContains(name, pageable);
        if(data.isEmpty()) throw new MedicNotFoundException(name);
        return MedicDto.toListDto(data);
    }

    @SneakyThrows
    public MedicDto findById(Long id){
        var data = repository.findById(id).orElseThrow(MedicNotFoundException::new);
        return new MedicDto(data);
    }

    public MedicDto update(Long id, MedicDto data){
        var entity = data.toEntity();
        entity.setId(id);
        return MedicDto.toDto(repository.save(entity));
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
