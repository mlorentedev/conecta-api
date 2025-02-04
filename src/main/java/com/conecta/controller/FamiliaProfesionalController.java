package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.FamiliaProfesionalDTO;
import com.conecta.model.FamiliaProfesional;
import com.conecta.service.FamiliaProfesionalService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/familias-profesionales")
public class FamiliaProfesionalController {

    private final FamiliaProfesionalService familiaProfesionalService;

    public FamiliaProfesionalController(FamiliaProfesionalService familiaProfesionalService) {
        this.familiaProfesionalService = familiaProfesionalService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las familias profesionales", description = "Retorna una lista de todas las familias profesionales")
    @ApiResponse(responseCode = "200", description = "Lista de familias profesionales obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = FamiliaProfesionalDTO.class)))
    public ResponseEntity<List<FamiliaProfesionalDTO>> getAllFamiliasProfesionales() {
        List<FamiliaProfesionalDTO> familiasProfesionalesDTO = familiaProfesionalService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(familiasProfesionalesDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una familia profesional por ID", description = "Retorna una familia profesional basada en su ID")
    @ApiResponse(responseCode = "200", description = "Familia profesional encontrada", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = FamiliaProfesionalDTO.class)))
    @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada")
    public ResponseEntity<FamiliaProfesionalDTO> getFamiliaProfesionalById(
            @Parameter(description = "ID de la familia profesional a buscar", required = true)
            @PathVariable Long id) {
        return familiaProfesionalService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva familia profesional", description = "Crea una nueva familia profesional y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Familia profesional creada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = FamiliaProfesionalDTO.class)))
    public ResponseEntity<FamiliaProfesionalDTO> createFamiliaProfesional(
            @Parameter(description = "Datos de la familia profesional a crear", required = true)
            @RequestBody FamiliaProfesionalDTO familiaProfesionalDTO) {
        FamiliaProfesional createdFamiliaProfesional = familiaProfesionalService.create(familiaProfesionalDTO);
        return ResponseEntity.ok(convertToDTO(createdFamiliaProfesional));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una familia profesional existente", description = "Actualiza una familia profesional existente basada en su ID")
    @ApiResponse(responseCode = "200", description = "Familia profesional actualizada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = FamiliaProfesionalDTO.class)))
    @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada")
    public ResponseEntity<FamiliaProfesionalDTO> updateFamiliaProfesional(
            @Parameter(description = "ID de la familia profesional a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos de la familia profesional", required = true)
            @RequestBody FamiliaProfesionalDTO familiaProfesionalDTO) {
        return familiaProfesionalService.update(id, familiaProfesionalDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una familia profesional", description = "Elimina una familia profesional basada en su ID")
    @ApiResponse(responseCode = "204", description = "Familia profesional eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada")
    public ResponseEntity<Void> deleteFamiliaProfesional(
            @Parameter(description = "ID de la familia profesional a eliminar", required = true)
            @PathVariable Long id) {
        familiaProfesionalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private FamiliaProfesionalDTO convertToDTO(FamiliaProfesional familiaProfesional) {
        return new FamiliaProfesionalDTO(
            familiaProfesional.getId(),
            familiaProfesional.getNombre()
        );
    }
}
