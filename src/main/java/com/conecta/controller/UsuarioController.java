package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.CreateUsuarioDTO;
import com.conecta.dto.UpdateUsuarioDTO;
import com.conecta.dto.UsuarioDTO;
import com.conecta.model.Usuario;
import com.conecta.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Retorna un usuario basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar", required = true)
            @PathVariable("id") Long id) {
        UsuarioDTO usuario = UsuarioDTO.fromEntity(usuarioService.findById(id).get());
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario y retorna los datos creados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> createUsuario(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody CreateUsuarioDTO usuarioDTO) {
        Usuario createdUsuario = usuarioService.create(usuarioDTO);
        return ResponseEntity.ok(UsuarioDTO.fromEntity(createdUsuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza un usuario existente basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @RequestBody UpdateUsuarioDTO usuarioDTO) {
        return usuarioService.update(id, usuarioDTO)
                .map(usuario -> ResponseEntity.ok(UsuarioDTO.fromEntity(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable("id") Long id) {
        if (usuarioService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
