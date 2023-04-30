package br.edu.ifba.medicalclinic.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import jakarta.transaction.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/medics")
public class MedicController {
    
    @Autowired
    private MedicService service;

    @PostMapping
    @Operation(summary = "Save only one medic")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Saved with success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Not Acceptable",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<MedicDto> save(
            @Parameter(description = "New medic body content to be created")
            @RequestBody MedicDto data,
            UriComponentsBuilder builder
    ){
        var dataSaved = service.save(data);
        var uri = builder.path("/medics/{id}").buildAndExpand(dataSaved.id()).toUri();
        return ResponseEntity.created(uri).body(dataSaved);
    }

    @GetMapping
    @Operation(summary = "Retrieve all medics with or without filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retrieval of successful contacts",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<List<MedicDto>> find(
            @Parameter(description = "Name for medic to be found (optional)")
            @RequestParam(required = false) String name,
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
;       var data = service.find(name, pageable).get();
        var isEmpty = data.isEmpty();     
        return isEmpty ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update only one medic")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated with successful",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<MedicDto> update(
            @Parameter(description = "Medic Id to be updated")
            @PathVariable Long id,
            @Parameter(description = "Medic Elements/Body Content to be updated")
            @RequestBody MedicDto data
    ){
        return service.findById(id)
            .map(record -> {
                var dataUpdated = service.update(id, data);
                return ResponseEntity.ok().body(dataUpdated);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete only one medic")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted with successful",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MedicDto.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<MedicDto> deleteById(
            @Parameter(description = "Medic Id to be deleted")
            @PathVariable Long id
    ){
        return service.findById(id)
            .map(record -> {
                service.deleteById(id);
                return ResponseEntity.ok().body(record);
            }).orElse(ResponseEntity.notFound().build());
    }
}
