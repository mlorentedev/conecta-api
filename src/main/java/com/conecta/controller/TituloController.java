package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUpdateTituloDTO;
import com.conecta.dto.TituloDTO;
import com.conecta.model.Titulo;
import com.conecta.service.TituloService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/titulos")
public class TituloController {

    private final TituloService tituloService;

    public TituloController(TituloService tituloService) {
        this.tituloService = tituloService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los títulos", description = "Retorna una lista de todos los títulos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de títulos encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TituloDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<TituloDTO>> getAllTitulos() {
        List<TituloDTO> titulosDTO = tituloService.findAll().stream()
                .map(TituloDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(titulosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un título por ID", description = "Retorna un título basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Título encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TituloDTO.class))),
        @ApiResponse(responseCode = "404", description = "Título no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TituloDTO> getTituloById(
        @Parameter(description = "ID del título a buscar") 
        @PathVariable("id") Long id) {
        TituloDTO tituloDTO = TituloDTO.fromEntity(tituloService.findById(id).get());
        return ResponseEntity.ok(tituloDTO);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo título", description = "Crea un nuevo título")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Título creado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TituloDTO.class))),
        @ApiResponse(responseCode = "400", description = "Petición inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TituloDTO> createTitulo(@RequestBody CreateUpdateTituloDTO tituloDTO) {
        Titulo titulo = tituloService.create(tituloDTO);
        return ResponseEntity.ok(TituloDTO.fromEntity(titulo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un título", description = "Actualiza un título basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Título actualizado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TituloDTO.class))),
        @ApiResponse(responseCode = "404", description = "Título no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TituloDTO> updateTitulo(
            @Parameter(description = "ID del título a actualizar") 
            @PathVariable("id") Long id,
            @RequestBody CreateUpdateTituloDTO tituloDTO) {
        return tituloService.update(id, tituloDTO)
                .map(titulo -> ResponseEntity.ok(TituloDTO.fromEntity(titulo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un título", description = "Elimina un título basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Título eliminado"),
        @ApiResponse(responseCode = "404", description = "Título no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteTitulo(
        @Parameter(description = "ID del título a eliminar") 
        @PathVariable("id") Long id) {
        if (!tituloService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
