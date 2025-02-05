package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUpdateProfesorDTO;
import com.conecta.dto.ProfesorDTO;
import com.conecta.model.Profesor;
import com.conecta.service.ProfesorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los profesores", description = "Retorna una lista de todos los profesores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de profesores encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProfesorDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProfesorDTO>> getAllProfesores() {
        List<ProfesorDTO> profesoresDTO = profesorService.findAll().stream()
                .map(ProfesorDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(profesoresDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un profesor por ID", description = "Retorna un profesor basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profesor encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProfesorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProfesorDTO> getProfesorById(
            @Parameter(description = "ID del profesor a buscar", required = true)
            @PathVariable("id") Long id) {
        ProfesorDTO profesor = ProfesorDTO.fromEntity(profesorService.findById(id).get());
        return ResponseEntity.ok(profesor);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo profesor", description = "Crea un nuevo profesor y retorna los datos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profesor creado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProfesorDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProfesorDTO> createProfesor(
            @Parameter(description = "Datos del profesor a crear", required = true)
            @RequestBody CreateUpdateProfesorDTO profesorDTO) {
        Profesor createdProfesor = profesorService.create(profesorDTO);
        return ResponseEntity.ok(ProfesorDTO.fromEntity(createdProfesor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un profesor existente", description = "Actualiza un profesor existente basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profesor actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProfesorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProfesorDTO> updateProfesor(
            @Parameter(description = "ID del profesor a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del profesor", required = true)
            @RequestBody CreateUpdateProfesorDTO profesorDTO) {
        return profesorService.update(id, profesorDTO)
                .map(profesor -> ResponseEntity.ok(ProfesorDTO.fromEntity(profesor)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un profesor", description = "Elimina un profesor basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Profesor eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Profesor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteProfesor(
            @Parameter(description = "ID del profesor a eliminar", required = true)
            @PathVariable("id") Long id) {
        if (profesorService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}