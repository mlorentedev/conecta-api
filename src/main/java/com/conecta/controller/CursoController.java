package com.conecta.controller;

import com.conecta.dto.CursoDTO;
import com.conecta.model.Curso;
import com.conecta.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cursos", description = "Retorna una lista de todos los cursos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CursoDTO.class)))
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        List<CursoDTO> cursosDTO = cursoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cursosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un curso por ID", description = "Retorna un curso basado en su ID")
    @ApiResponse(responseCode = "200", description = "Curso encontrado", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CursoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<CursoDTO> getCursoById(
            @Parameter(description = "ID del curso a buscar", required = true)
            @PathVariable Long id) {
        return cursoService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo curso", description = "Crea un nuevo curso y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Curso creado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CursoDTO.class)))
    public ResponseEntity<CursoDTO> createCurso(
            @Parameter(description = "Datos del curso a crear", required = true)
            @RequestBody CursoDTO cursoDTO) {
        Curso createdCurso = cursoService.create(cursoDTO);
        return ResponseEntity.ok(convertToDTO(createdCurso));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un curso existente", description = "Actualiza un curso existente basado en su ID")
    @ApiResponse(responseCode = "200", description = "Curso actualizado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CursoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<CursoDTO> updateCurso(
            @Parameter(description = "ID del curso a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos del curso", required = true)
            @RequestBody CursoDTO cursoDTO) {
        return cursoService.update(id, cursoDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un curso", description = "Elimina un curso basado en su ID")
    @ApiResponse(responseCode = "204", description = "Curso eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<Void> deleteCurso(
            @Parameter(description = "ID del curso a eliminar", required = true)
            @PathVariable Long id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CursoDTO convertToDTO(Curso curso) {
        return new CursoDTO(
            curso.getId(),
            curso.getNombre(),
            curso.getHorasEmpresa(),
            curso.getProfesor() != null ? curso.getProfesor().getId() : null,
            curso.getTitulo() != null ? curso.getTitulo().getId() : null
        );
    }
}