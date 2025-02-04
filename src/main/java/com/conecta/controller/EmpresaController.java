package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.EmpresaDTO;
import com.conecta.dto.FamiliaProfesionalDTO;
import com.conecta.dto.TrabajadorDTO;
import com.conecta.exception.ResourceNotFoundException;
import com.conecta.model.Empresa;
import com.conecta.model.Trabajador;
import com.conecta.service.EmpresaService;
import com.conecta.service.TrabajadorService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final TrabajadorService trabajadorService;

    public EmpresaController(EmpresaService empresaService, TrabajadorService trabajadorService) {
        this.empresaService = empresaService;
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las empresas", description = "Retorna una lista de todas las empresas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EmpresaDTO.class)))
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas() {
        List<EmpresaDTO> empresasDTO = empresaService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una empresa por ID", description = "Retorna una empresa basada en su ID")
    @ApiResponse(responseCode = "200", description = "Empresa encontrada", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EmpresaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    public ResponseEntity<EmpresaDTO> getEmpresaById(
            @Parameter(description = "ID de la empresa a buscar", required = true)
            @PathVariable Long id) {
        return empresaService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva empresa", description = "Crea una nueva empresa y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Empresa creada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EmpresaDTO.class)))
    public ResponseEntity<EmpresaDTO> createEmpresa(
            @Parameter(description = "Datos de la empresa a crear", required = true)
            @RequestBody EmpresaDTO empresaDTO) {
        Empresa createdEmpresa = empresaService.create(empresaDTO);
        return ResponseEntity.ok(convertToDTO(createdEmpresa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una empresa existente", description = "Actualiza una empresa existente basada en su ID")
    @ApiResponse(responseCode = "200", description = "Empresa actualizada con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EmpresaDTO.class)))
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    public ResponseEntity<EmpresaDTO> updateEmpresa(
            @Parameter(description = "ID de la empresa a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos de la empresa", required = true)
            @RequestBody EmpresaDTO empresaDTO) {
        return empresaService.update(id, empresaDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa", description = "Elimina una empresa basada en su ID")
    @ApiResponse(responseCode = "204", description = "Empresa eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    public ResponseEntity<Void> deleteEmpresa(
            @Parameter(description = "ID de la empresa a eliminar", required = true)
            @PathVariable Long id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/trabajadores")
    @Operation(summary = "Obtener trabajadores de una empresa", description = "Retorna una lista de trabajadores de una empresa específica")
    @ApiResponse(responseCode = "200", description = "Lista de trabajadores obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = TrabajadorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    public ResponseEntity<List<TrabajadorDTO>> getTrabajadoresByEmpresa(
            @Parameter(description = "ID de la empresa", required = true)
            @PathVariable Long id) {
        List<TrabajadorDTO> trabajadoresDTO = trabajadorService.findByEmpresaId(id).stream()
                .map(this::convertTrabajadorToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trabajadoresDTO);
    }

    @PostMapping("/{empresaId}/familias-profesionales/{familiaProfesionalId}")
    @Operation(summary = "Agregar una familia profesional a una empresa")
    public ResponseEntity<Void> addFamiliaProfesional(
            @PathVariable Long empresaId,
            @PathVariable Long familiaProfesionalId) throws ResourceNotFoundException {
        empresaService.addFamiliaProfesional(empresaId, familiaProfesionalId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{empresaId}/familias-profesionales/{familiaProfesionalId}")
    @Operation(summary = "Eliminar una familia profesional de una empresa")
    public ResponseEntity<Void> removeFamiliaProfesional(
            @PathVariable Long empresaId,
            @PathVariable Long familiaProfesionalId) throws ResourceNotFoundException {
        empresaService.removeFamiliaProfesional(empresaId, familiaProfesionalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{empresaId}/familias-profesionales")
    @Operation(summary = "Obtener todas las familias profesionales de una empresa")
    public ResponseEntity<Set<FamiliaProfesionalDTO>> getFamiliasProfesionales(
            @PathVariable Long empresaId) throws ResourceNotFoundException {
        Set<FamiliaProfesionalDTO> familiasProfesionales = empresaService.getFamiliasProfesionales(empresaId);
        return ResponseEntity.ok(familiasProfesionales);
    }

    private EmpresaDTO convertToDTO(Empresa empresa) {
        return new EmpresaDTO(
            empresa.getId(),
            empresa.getCif(),
            empresa.getDireccion(),
            empresa.getCoordenadas(),
            empresa.getNombre()
        );
    }

    private TrabajadorDTO convertTrabajadorToDTO(Trabajador trabajador) {
        return new TrabajadorDTO(
            trabajador.getId(),
            trabajador.getNombre(),
            trabajador.getApellidos(),
            trabajador.getEmail(),
            trabajador.getTelefono(),
            trabajador.getPuesto(),
            trabajador.getArea(),
            trabajador.getEmpresa().getId()
        );
    }
}
