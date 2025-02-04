package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponse(responseCode = "200", description = "Lista de títulos obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TituloDTO.class)))
    public ResponseEntity<List<TituloDTO>> getAllTitulos() {
        List<TituloDTO> titulosDTO = tituloService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(titulosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un título por ID", description = "Retorna un título basado en su ID")
    @ApiResponse(responseCode = "200", description = "Título encontrado", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TituloDTO.class)))
    @ApiResponse(responseCode = "404", description = "Título no encontrado")
    public ResponseEntity<TituloDTO> getTituloById(
            @Parameter(description = "ID del título a buscar", required = true)
            @PathVariable Long id) {
        return tituloService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo título", description = "Crea un nuevo título y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Título creado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TituloDTO.class)))
    public ResponseEntity<TituloDTO> createTitulo(
            @Parameter(description = "Datos del título a crear", required = true)
            @RequestBody TituloDTO tituloDTO) {
        Titulo createdTitulo = tituloService.create(tituloDTO);
        return ResponseEntity.ok(convertToDTO(createdTitulo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un título existente", description = "Actualiza un título existente basado en su ID")
    @ApiResponse(responseCode = "200", description = "Título actualizado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TituloDTO.class)))
    @ApiResponse(responseCode = "404", description = "Título no encontrado")
    public ResponseEntity<TituloDTO> updateTitulo(
            @Parameter(description = "ID del título a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos del título", required = true)
            @RequestBody TituloDTO tituloDTO) {
        return tituloService.update(id, tituloDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un título", description = "Elimina un título basado en su ID")
    @ApiResponse(responseCode = "204", description = "Título eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Título no encontrado")
    public ResponseEntity<Void> deleteTitulo(
            @Parameter(description = "ID del título a eliminar", required = true)
            @PathVariable Long id) {
        tituloService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/familia-profesional/{familiaProfesionalId}")
    @Operation(summary = "Obtener títulos por familia profesional", description = "Retorna una lista de títulos asociados a una familia profesional")
    @ApiResponse(responseCode = "200", description = "Lista de títulos obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TituloDTO.class)))
    public ResponseEntity<List<TituloDTO>> getTitulosByFamiliaProfesional(
            @Parameter(description = "ID de la familia profesional", required = true)
            @PathVariable Long familiaProfesionalId) {
        List<TituloDTO> titulosDTO = tituloService.findByFamiliaProfesionalId(familiaProfesionalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(titulosDTO);
    }

    private TituloDTO convertToDTO(Titulo titulo) {
        return new TituloDTO(
            titulo.getId(),
            titulo.getNombre(),
            titulo.getDuracion(),
            titulo.getGrado(),
            titulo.getFamiliaProfesional().getId()
        );
    }
}
