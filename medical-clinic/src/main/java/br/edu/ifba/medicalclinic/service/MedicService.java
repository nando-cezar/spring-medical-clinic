package br.edu.ifba.medicalclinic.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<List<MedicDto>> find(String name, Pageable pageable){
        if(name == null) return Optional.of(MedicDto.toListDto(repository.findAll(pageable).toList()));
        return Optional.of(MedicDto.toListDto(repository.findByNameContains(name, pageable)));
    }

    public Optional<MedicDto> findById(Long id){
        return repository.findById(id).map(MedicDto::new);
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
