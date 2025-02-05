package com.conecta.dto;

import com.conecta.model.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un usuario")
public record CreateUsuarioDTO(

@Schema(description = "Nombre de usuario", example = "ana.martin")
    String username,

    @Schema(description = "Contraseña de usuario", example = "123456")
    String password,

    @Schema(description = "Rol de usuario", example = "ADMIN")
    String role

) {
    
    public static CreateUsuarioDTO fromEntity(Usuario usuario) {
        return new CreateUsuarioDTO(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRole()
        );
    }
}