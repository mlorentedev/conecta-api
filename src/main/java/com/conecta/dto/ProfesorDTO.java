package com.conecta.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un profesor")
public record ProfesorDTO(
    @Schema(description = "ID del profesor", example = "1")
    Long id,

    @Schema(description = "Nombre del profesor", example = "Juan")
    String nombre,

    @Schema(description = "Apellidos del profesor", example = "García Martínez")
    String apellidos,

    @Schema(description = "Email del profesor", example = "juan.garcia@instituto.edu")
    String email,

    @Schema(description = "Teléfono del profesor", example = "666111222")
    String telefono
) {}