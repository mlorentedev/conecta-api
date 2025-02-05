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
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener demandas por ID de empresa", description = "Retorna una lista de demandas basadas en el ID de la empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getDemandasByEmpresaId(
        @Parameter(description = "ID de la empresa a buscar") 
        @PathVariable("empresaId") Long empresaId) {
        List<DemandaDTO> demandasDTO = demandaService.findByEmpresaId(empresaId).stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Obtener demandas por ID de curso", description = "Retorna una lista de demandas basadas en el ID del curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getDemandasByCursoId(
        @Parameter(description = "ID del curso a buscar") 
        @PathVariable("cursoId") Long cursoId) {
        List<DemandaDTO> demandasDTO = demandaService.findByCursoId(cursoId).stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/empresa/{empresaId}/curso-escolar/{cursoEscolar}")
    @Operation(summary = "Obtener demandas por ID de empresa y curso escolar", description = "Retorna una lista de demandas basadas en el ID de la empresa y el curso escolar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getDemandasByEmpresaIdAndCursoEscolar(
        @Parameter(description = "ID de la empresa a buscar") 
        @PathVariable("empresaId") Long empresaId,
        @Parameter(description = "Curso escolar a buscar") 
        @PathVariable("cursoEscolar") String cursoEscolar) {
        List<DemandaDTO> demandasDTO = demandaService.findByEmpresaIdAndCursoEscolar(empresaId, cursoEscolar).stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/familia-profesional/{familiaId}")
    @Operation(summary = "Obtener demandas por ID de familia profesional", description = "Retorna una lista de demandas basadas en el ID de la familia profesional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getDemandasByFamiliaId(
        @Parameter(description = "ID de la familia profesional a buscar") 
        @PathVariable("familiaId") Long familiaId) {
        List<DemandaDTO> demandasDTO = demandaService.findByFamiliaId(familiaId).stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

    @GetMapping("/profesor/{profesorId}")
    @Operation(summary = "Obtener demandas por ID de profesor", description = "Retorna una lista de demandas basadas en el ID del profesor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de demandas encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = DemandaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DemandaDTO>> getDemandasByProfesorId(
        @Parameter(description = "ID del profesor a buscar") 
        @PathVariable("profesorId") Long profesorId) {
        List<DemandaDTO> demandasDTO = demandaService.findByProfesorId(profesorId).stream()
                .map(DemandaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandasDTO);
    }

}
