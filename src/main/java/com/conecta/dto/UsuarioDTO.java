package com.conecta.dto;

import com.conecta.model.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un usuario")
public record UsuarioDTO(
    @Schema(description = "ID del usuario", example = "1")
    Long id,

    @Schema(description = "Nombre de usuario", example = "ana.martin")
    String username,

    @Schema(description = "Contraseña de usuario", example = "123456")
    String password,

    @Schema(description = "Rol de usuario", example = "ADMIN")
    String role,

    @Schema(description = "ID del profesor del usuario", example = "1")
    Long profesorId
) {
    
    public static UsuarioDTO fromEntity(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole(),
                usuario.getProfesor().getId()
        );
    }
}