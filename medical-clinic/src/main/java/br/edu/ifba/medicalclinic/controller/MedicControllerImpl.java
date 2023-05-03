package br.edu.ifba.medicalclinic.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.medicalclinic.domain.dto.MedicDto;
import br.edu.ifba.medicalclinic.service.MedicService;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/medics")
public class MedicControllerImpl implements MedicController {
    
    @Autowired
    private MedicService service;

    @Override
    @PostMapping
    public ResponseEntity<MedicDto> save(
            @Parameter(description = "New medic body content to be created")
            @Valid
            @RequestBody MedicDto data,
            UriComponentsBuilder builder
    ){
        var dataSaved = service.save(data);
        var uri = builder.path("/medics/{id}").buildAndExpand(dataSaved.id()).toUri();
        return ResponseEntity.created(uri).body(dataSaved);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MedicDto>> find(
            @Parameter(description = "Name for medic to be found (optional)")
            @RequestParam(required = false) String name,
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        var data = service.find(name, pageable);
        return ResponseEntity.ok().body(data);
    }

    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicDto> update(
            @Parameter(description = "Medic Id to be updated")
            @PathVariable Long id,
            @Valid
            @Parameter(description = "Medic Elements/Body Content to be updated")
            @RequestBody MedicDto data
    ){
        service.findById(id);
        var dataUpdated = service.update(id, data);
        return ResponseEntity.ok().body(dataUpdated);
    }

    @Override
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicDto> deleteById(
            @Parameter(description = "Medic Id to be deleted")
            @PathVariable Long id
    ){
        var data = service.findById(id);
        service.deleteById(id);
        return ResponseEntity.ok().body(data);
    }
}