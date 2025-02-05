package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUpdateEmpresaDTO;
import com.conecta.dto.EmpresaDTO;
import com.conecta.model.Empresa;
import com.conecta.service.EmpresaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las empresas", description = "Retorna una lista de todas las empresas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EmpresaDTO.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron empresas")
    })
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas() {
        List<EmpresaDTO> empresasDTO = empresaService.findAll().stream()
                .map(EmpresaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una empresa por ID", description = "Retorna una empresa basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = EmpresaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    public ResponseEntity<EmpresaDTO> getEmpresaById(
            @Parameter(description = "ID de la empresa a buscar", required = true)
            @PathVariable("id") Long id) {
        EmpresaDTO empresa = EmpresaDTO.fromEntity(empresaService.findById(id).get());
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva empresa", description = "Crea una nueva empresa y retorna los datos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa creada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CreateUpdateEmpresaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CreateUpdateEmpresaDTO> createEmpresa(
            @Parameter(description = "Datos de la empresa a crear", required = true)
            @RequestBody CreateUpdateEmpresaDTO empresaDTO) {
        Empresa createdEmpresa = empresaService.create(empresaDTO);
        return ResponseEntity.ok(CreateUpdateEmpresaDTO.fromEntity(createdEmpresa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una empresa existente", description = "Actualiza una empresa existente basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa actualizada con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CreateUpdateEmpresaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EmpresaDTO> updateEmpresa(
            @Parameter(description = "ID de la empresa a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos de la empresa", required = true)
            @RequestBody CreateUpdateEmpresaDTO empresaDTO) {
        return empresaService.update(id, empresaDTO)
                .map(empresa -> ResponseEntity.ok(EmpresaDTO.fromEntity(empresa)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa", description = "Elimina una empresa basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empresa eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteEmpresa(
            @Parameter(description = "ID de la empresa a eliminar", required = true)
            @PathVariable("id") Long id) {
        if (empresaService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
