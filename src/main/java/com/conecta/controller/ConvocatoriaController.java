package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.ConvocatoriaDTO;
import com.conecta.dto.CreateUpdateConvocatoriaDTO;
import com.conecta.model.Convocatoria;
import com.conecta.service.ConvocatoriaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaController {

    private final ConvocatoriaService convocatoriaService;

    public ConvocatoriaController(ConvocatoriaService convocatoriaService) {
        this.convocatoriaService = convocatoriaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las convocatorias", description = "Retorna una lista de todas las convocatorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convocatorias listadas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConvocatoriaDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    public ResponseEntity<List<ConvocatoriaDTO>> findAll() {
        List<Convocatoria> convocatorias = convocatoriaService.findAll();
        List<ConvocatoriaDTO> convocatoriasDTO = convocatorias.stream()
                .map(ConvocatoriaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(convocatoriasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener convocatoria por id", description = "Retorna una convocatoria por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convocatoria encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConvocatoriaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    public ResponseEntity<ConvocatoriaDTO> findById(
            @Parameter(description = "Id de la convocatoria", required = true)
            @PathVariable("id") Long id) {
        return convocatoriaService.findById(id)
                .map(ConvocatoriaDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear convocatoria", description = "Crea una nueva convocatoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convocatoria creada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConvocatoriaDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    public ResponseEntity<ConvocatoriaDTO> create(
            @Parameter(description = "Datos de la convocatoria", required = true)
            @RequestBody CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        Convocatoria convocatoria = convocatoriaService.create(convocatoriaDTO);
        return ResponseEntity.ok(ConvocatoriaDTO.fromEntity(convocatoria));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar convocatoria", description = "Actualiza una convocatoria por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convocatoria actualizada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConvocatoriaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    public ResponseEntity<ConvocatoriaDTO> update(
            @Parameter(description = "Id de la convocatoria", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Datos de la convocatoria", required = true)
            @RequestBody CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        return convocatoriaService.update(id, convocatoriaDTO)
                .map(ConvocatoriaDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar convocatoria", description = "Elimina una convocatoria por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convocatoria eliminada"),
            @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la convocatoria", required = true)
            @PathVariable("id") Long id) {
        return convocatoriaService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
