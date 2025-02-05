package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUpdateDemandaDTO;
import com.conecta.dto.DemandaDTO;
import com.conecta.model.Demanda;
import com.conecta.service.DemandaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/demandas")
public class DemandaController {

    private final DemandaService demandaService;

    public DemandaController(DemandaService demandaService) {
        this.demandaService = demandaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las demandas", description = "Retorna una lista de todas las demandas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getAllDemandas() {
        List<DemandaDTO> demandasDTO = demandaService.findAll().stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una demanda por ID", description = "Retorna una demanda basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Demanda encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Demanda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DemandaDTO> getDemandaById(
        @Parameter(description = "ID de la demanda a buscar") 
        @PathVariable("id") Long id) {
        DemandaDTO demandaDTO = DemandaDTO.fromEntity(demandaService.findById(id).get());
        return ResponseEntity.ok(demandaDTO);
    }

    @PostMapping
    @Operation(summary = "Crear una demanda", description = "Crea una nueva demanda")
    public ResponseEntity<DemandaDTO> createDemanda(
        @Parameter(description = "Datos de la demanda a crear") 
        @RequestBody CreateUpdateDemandaDTO demandaDTO) {
        Demanda demanda = demandaService.create(demandaDTO);
        return ResponseEntity.ok(DemandaDTO.fromEntity(demanda));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una demanda", description = "Actualiza una demanda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Demanda actualizada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Demanda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DemandaDTO> updateDemanda(
        @Parameter(description = "ID de la demanda a actualizar") 
        @PathVariable("id") Long id,
        @Parameter(description = "Datos de la demanda a actualizar") 
        @RequestBody CreateUpdateDemandaDTO demandaDTO) {
        Demanda demanda = demandaService.update(id, demandaDTO).get();
        return ResponseEntity.ok(DemandaDTO.fromEntity(demanda));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una demanda", description = "Elimina una demanda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Demanda eliminada"),
        @ApiResponse(responseCode = "404", description = "Demanda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteDemanda(
        @Parameter(description = "ID de la demanda a eliminar") 
        @PathVariable("id") Long id) {
        demandaService.delete(id);
        return ResponseEntity.ok().build();
    }


}
