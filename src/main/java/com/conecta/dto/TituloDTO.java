package com.conecta.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un título")
public record TituloDTO(
    @Schema(description = "ID del título", example = "1")
    Long id,

    @Schema(description = "Nombre del título", example = "Desarrollo de Aplicaciones Web")
    String nombre,

    @Schema(description = "Duración del título", example = "2000 horas")
    String duracion,

    @Schema(description = "Grado del título", example = "Superior")
    String grado,

    @Schema(description = "ID de la familia profesional", example = "1")
    Long familiaProfesionalId
) {}