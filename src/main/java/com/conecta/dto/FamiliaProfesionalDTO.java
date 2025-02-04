package com.conecta.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una familia profesional")
public record FamiliaProfesionalDTO(
    @Schema(description = "ID de la familia profesional", example = "1")
    Long id,

    @Schema(description = "Nombre de la familia profesional", example = "Informática y Comunicaciones")
    String nombre
) {}