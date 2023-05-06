package br.edu.ifba.medicalclinic.controller;

import br.edu.ifba.medicalclinic.domain.dto.MedicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface MedicController {
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
    ResponseEntity<MedicDto> save(
            @Parameter(description = "New medic body content to be created")
            MedicDto data,
            UriComponentsBuilder builder
    );

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
    ResponseEntity<List<MedicDto>> find(
            @Parameter(description = "Name for medic to be found (optional)")
            String name,
            Pageable pageable
    );

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
    ResponseEntity<MedicDto> update(
            @Parameter(description = "Medic Id to be updated")
            @PathVariable Long id,
            @Parameter(description = "Medic Elements/Body Content to be updated")
            MedicDto data
    );

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
    ResponseEntity<MedicDto> deleteById(
            @Parameter(description = "Medic Id to be deleted")
            Long id
    );
}
