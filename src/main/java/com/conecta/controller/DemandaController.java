package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponse(responseCode = "200", description = "Lista de demandas obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    public ResponseEntity<List<DemandaDTO>> getAllDemandas() {
        List<DemandaDTO> demandasDTO = demandaService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una demanda por ID", description = "Retorna una demanda basada en su ID")
    @ApiResponse(responseCode = "200", description = "Demanda encontrada", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Demanda no encontrada")
    public ResponseEntity<DemandaDTO> getDemandaById(
            @Parameter(description = "ID de la demanda a buscar", required = true)
            @PathVariable Long id) {
        return demandaService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva demanda", description = "Crea una nueva demanda y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Demanda creada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    public ResponseEntity<DemandaDTO> createDemanda(
            @Parameter(description = "Datos de la demanda a crear", required = true)
            @RequestBody DemandaDTO demandaDTO) {
        Demanda createdDemanda = demandaService.create(demandaDTO);
        return ResponseEntity.ok(convertToDTO(createdDemanda));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una demanda existente", description = "Actualiza una demanda existente basada en su ID")
    @ApiResponse(responseCode = "200", description = "Demanda actualizada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Demanda no encontrada")
    public ResponseEntity<DemandaDTO> updateDemanda(
            @Parameter(description = "ID de la demanda a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos de la demanda", required = true)
            @RequestBody DemandaDTO demandaDTO) {
        return demandaService.update(id, demandaDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una demanda", description = "Elimina una demanda basada en su ID")
    @ApiResponse(responseCode = "204", description = "Demanda eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Demanda no encontrada")
    public ResponseEntity<Void> deleteDemanda(
            @Parameter(description = "ID de la demanda a eliminar", required = true)
            @PathVariable Long id) {
        demandaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener demandas por empresa", description = "Retorna una lista de demandas asociadas a una empresa")
    @ApiResponse(responseCode = "200", description = "Lista de demandas obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    public ResponseEntity<List<DemandaDTO>> getDemandasByEmpresa(
            @Parameter(description = "ID de la empresa", required = true)
            @PathVariable Long empresaId) {
        List<DemandaDTO> demandasDTO = demandaService.findByEmpresaId(empresaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Obtener demandas por curso", description = "Retorna una lista de demandas asociadas a un curso")
    @ApiResponse(responseCode = "200", description = "Lista de demandas obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DemandaDTO.class)))
    public ResponseEntity<List<DemandaDTO>> getDemandasByCurso(
            @Parameter(description = "ID del curso", required = true)
            @PathVariable Long cursoId) {
        List<DemandaDTO> demandasDTO = demandaService.findByCursoId(cursoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    private DemandaDTO convertToDTO(Demanda demanda) {
        return new DemandaDTO(
            demanda.getId(),
            demanda.getCantidadAlumnos(),
            demanda.getRequisitos(),
            demanda.getEmpresa().getId(),
            demanda.getCurso().getId()
        );
    }
}
