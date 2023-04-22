package br.edu.ifba.medicalclinic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/medics")
public class MedicController {
    
    @Autowired
    private MedicService service;

    @PostMapping
    public ResponseEntity<MedicDto> save(@RequestBody MedicDto data){
        var dataSaved = service.save(data);
        return new ResponseEntity<MedicDto>(dataSaved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicDto>> find(@RequestParam(required = false) String name){
        var data = service.find(name).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<MedicDto> update(@PathVariable Long id, @RequestBody MedicDto data){
        return service.findById(id)
            .map(record -> {
                var dataUpdated = service.update(id, data);
                return ResponseEntity.ok().body(dataUpdated);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<MedicDto> deleteById(@PathVariable Long id){
        return service.findById(id)
            .map(record -> {
                service.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
