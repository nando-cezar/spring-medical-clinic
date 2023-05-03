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

    /**
     * @param data The body content to be created
     * @return a {@code ResponseEntity} instance
     */
    public MedicDto save(MedicDto data){
        var entity = data.toEntity();
        return MedicDto.toDto(repository.save(entity));
    }

    /**
     * @param name The param to filter (optional)
     * @param pageable The param to pagination
     * @return a {@code ResponseEntity} instance
     */
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

    /**
     * @param id The id to be searched
     * @return a {@code ResponseEntity} instance
     */
    @SneakyThrows
    public MedicDto findById(Long id){
        var data = repository.findById(id).orElseThrow(MedicNotFoundException::new);
        return new MedicDto(data);
    }

    /**
     * @param id The id to be updated
     * @param data The elements/Body Content to be updated
     * @return a {@code ResponseEntity} instance
     */
    public MedicDto update(Long id, MedicDto data){
        var entity = data.toEntity();
        entity.setId(id);
        return MedicDto.toDto(repository.save(entity));
    }

    /**
     * @param id The id to be deleted
     */
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
