package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.ConvocatoriaDTO;
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
    @ApiResponse(responseCode = "200", description = "Lista de convocatorias obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ConvocatoriaDTO.class)))
    public ResponseEntity<List<ConvocatoriaDTO>> getAllConvocatorias() {
        List<ConvocatoriaDTO> convocatoriasDTO = convocatoriaService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(convocatoriasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una convocatoria por ID", description = "Retorna una convocatoria basada en su ID")
    @ApiResponse(responseCode = "200", description = "Convocatoria encontrada", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ConvocatoriaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada")
    public ResponseEntity<ConvocatoriaDTO> getConvocatoriaById(
            @Parameter(description = "ID de la convocatoria a buscar", required = true)
            @PathVariable Long id) {
        return convocatoriaService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva convocatoria", description = "Crea una nueva convocatoria y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Convocatoria creada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ConvocatoriaDTO.class)))
    public ResponseEntity<ConvocatoriaDTO> createConvocatoria(
            @Parameter(description = "Datos de la convocatoria a crear", required = true)
            @RequestBody ConvocatoriaDTO convocatoriaDTO) {
        Convocatoria createdConvocatoria = convocatoriaService.create(convocatoriaDTO);
        return ResponseEntity.ok(convertToDTO(createdConvocatoria));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una convocatoria existente", description = "Actualiza una convocatoria existente basada en su ID")
    @ApiResponse(responseCode = "200", description = "Convocatoria actualizada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ConvocatoriaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada")
    public ResponseEntity<ConvocatoriaDTO> updateConvocatoria(
            @Parameter(description = "ID de la convocatoria a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos de la convocatoria", required = true)
            @RequestBody ConvocatoriaDTO convocatoriaDTO) {
        return convocatoriaService.update(id, convocatoriaDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una convocatoria", description = "Elimina una convocatoria basada en su ID")
    @ApiResponse(responseCode = "204", description = "Convocatoria eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Convocatoria no encontrada")
    public ResponseEntity<Void> deleteConvocatoria(
            @Parameter(description = "ID de la convocatoria a eliminar", required = true)
            @PathVariable Long id) {
        convocatoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ConvocatoriaDTO convertToDTO(Convocatoria convocatoria) {
        return new ConvocatoriaDTO(
            convocatoria.getId(),
            convocatoria.getCursoEscolar(),
            convocatoria.getNombre(),
            convocatoria.getDemandas()
        );
    }
}
