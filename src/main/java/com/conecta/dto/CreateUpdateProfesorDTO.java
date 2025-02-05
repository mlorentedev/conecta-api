package com.conecta.dto;

import com.conecta.model.Profesor;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un profesor")
public record CreateUpdateProfesorDTO(

    @Schema(description = "Nombre del profesor", example = "Juan")
    String nombre,

    @Schema(description = "Apellidos del profesor", example = "García Martínez")
    String apellidos,

    @Schema(description = "Email del profesor", example = "juan.garcia@instituto.edu")
    String email,

    @Schema(description = "Teléfono del profesor", example = "666111222")
    String telefono
) {

    public static CreateUpdateProfesorDTO fromEntity(Profesor profesor) {
        return new CreateUpdateProfesorDTO(
                profesor.getNombre(),
                profesor.getApellidos(),
                profesor.getEmail(),
                profesor.getTelefono()
        );
    }
}
