package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.TrabajadorDTO;
import com.conecta.model.Trabajador;
import com.conecta.service.TrabajadorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los trabajadores", description = "Devuelve una lista de todos los trabajadores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de trabajadores encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TrabajadorDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<TrabajadorDTO>> getAllTrabajadores() {
        List<TrabajadorDTO> trabajadoresDTO = trabajadorService.findAll().stream()
                .map(TrabajadorDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trabajadoresDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un trabajador por ID", description = "Devuelve un trabajador basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trabajador encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TrabajadorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Trabajador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TrabajadorDTO> getTrabajadorById(
            @Parameter(description = "ID del trabajador a buscar", required = true)
            @PathVariable("id") Long id) {
        TrabajadorDTO trabajador = TrabajadorDTO.fromEntity(trabajadorService.findById(id).get());
        return ResponseEntity.ok(trabajador);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo trabajador", description = "Crea un nuevo trabajador y retorna los datos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trabajador creado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TrabajadorDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TrabajadorDTO> createTrabajador(
            @Parameter(description = "Datos del trabajador a crear", required = true)
            @RequestBody TrabajadorDTO trabajadorDTO) {
        Trabajador createdTrabajador = trabajadorService.create(trabajadorDTO);
        return ResponseEntity.ok(TrabajadorDTO.fromEntity(createdTrabajador));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un trabajador existente", description = "Actualiza un trabajador existente basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trabajador actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = TrabajadorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Trabajador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TrabajadorDTO> updateTrabajador(
            @Parameter(description = "ID del trabajador a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del trabajador", required = true)
            @RequestBody TrabajadorDTO trabajadorDTO) {
        return trabajadorService.update(id, trabajadorDTO)
                .map(trabajador -> ResponseEntity.ok(TrabajadorDTO.fromEntity(trabajador)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un trabajador", description = "Elimina un trabajador basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Trabajador eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Trabajador no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteTrabajador(
            @Parameter(description = "ID del trabajador a eliminar", required = true)
            @PathVariable("id") Long id) {
        if (trabajadorService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
