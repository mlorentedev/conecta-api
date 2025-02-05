package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUpdateFamiliaProfesionalDTO;
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

    @Operation(summary = "Listar familias profesionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Familias profesionales listadas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FamiliaProfesionalDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @GetMapping
    public ResponseEntity<List<FamiliaProfesionalDTO>> findAll() {
        List<FamiliaProfesional> familiasProfesionales = familiaProfesionalService.findAll();
        List<FamiliaProfesionalDTO> familiasProfesionalesDTO = familiasProfesionales.stream()
                .map(FamiliaProfesionalDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(familiasProfesionalesDTO);
    }

    @Operation(summary = "Obtener familia profesional por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Familia profesional encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FamiliaProfesionalDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @GetMapping("/{id}")
    public ResponseEntity<FamiliaProfesionalDTO> findById(
            @Parameter(description = "Id de la familia profesional", required = true) 
            @PathVariable("id") Long id) {
        return familiaProfesionalService.findById(id)
                .map(FamiliaProfesionalDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear familia profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Familia profesional creada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FamiliaProfesionalDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Petición incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @PostMapping
    public ResponseEntity<FamiliaProfesionalDTO> create(
            @Parameter(description = "Datos de la familia profesional", required = true) 
            @RequestBody CreateUpdateFamiliaProfesionalDTO familiaProfesionalDTO) {
        FamiliaProfesional familiaProfesional = familiaProfesionalService.create(familiaProfesionalDTO);
        return ResponseEntity.status(201).body(FamiliaProfesionalDTO.fromEntity(familiaProfesional));
    }

    @Operation(summary = "Actualizar familia profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Familia profesional actualizada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FamiliaProfesionalDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Petición incorrecta"),
            @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @PutMapping("/{id}")
    public ResponseEntity<FamiliaProfesionalDTO> update(
            @Parameter(description = "Id de la familia profesional", required = true) 
            @PathVariable("id") Long id,
            @Parameter(description = "Datos de la familia profesional", required = true) 
            @RequestBody CreateUpdateFamiliaProfesionalDTO familiaProfesionalDTO) {
        return familiaProfesionalService.update(id, familiaProfesionalDTO)
                .map(FamiliaProfesionalDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar familia profesional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Familia profesional eliminada"),
            @ApiResponse(responseCode = "404", description = "Familia profesional no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la familia profesional", required = true) 
            @PathVariable("id") Long id) {
        familiaProfesionalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}