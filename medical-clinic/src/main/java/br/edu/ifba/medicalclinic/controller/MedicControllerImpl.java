package br.edu.ifba.medicalclinic.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * @param data The body content to be created
     * @param builder Allows flexibility of using URI template
     *                variables and possibility to inject it directly.
     * @return a {@code ResponseEntity} instance
     */
    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<MedicDto> save(
            @Valid
            @RequestBody MedicDto data,
            UriComponentsBuilder builder
    ){
        var dataSaved = service.save(data);
        var uri = builder.path("/medics/{id}").buildAndExpand(dataSaved.id()).toUri();
        return ResponseEntity.created(uri).body(dataSaved);
    }

    /**
     * @param name The param to filter (optional)
     * @param pageable The pageable function
     * @return a {@code ResponseEntity} instance
     */
    @Override
    @GetMapping
    public ResponseEntity<List<MedicDto>> find(
            @RequestParam(required = false) String name,
            @PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable
    ){
        var data = service.find(name, pageable);
        return ResponseEntity.ok().body(data);
    }

    /**
     * @param id The id to be updated
     * @param data The elements/Body Content to be updated
     * @return a {@code ResponseEntity} instance
     */
    @Override
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicDto> update(
            @PathVariable Long id,
            @Valid
            @RequestBody MedicDto data
    ){
        service.findById(id);
        var dataUpdated = service.update(id, data);
        return ResponseEntity.ok().body(dataUpdated);
    }

    /**
     * @param id The id to be deleted
     * @return a {@code ResponseEntity} instance
     */
    @Override
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicDto> deleteById(
            @PathVariable Long id
    ){
        var data = service.findById(id);
        service.deleteById(id);
        return ResponseEntity.ok().body(data);
    }
}
