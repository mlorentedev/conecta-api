package com.conecta.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un curso")
public record CursoDTO(
    @Schema(description = "ID del curso", example = "1")
    Long id,

    @Schema(description = "Nombre del curso", example = "Desarrollo Web Full Stack")
    String nombre,

    @Schema(description = "Horas de prácticas en empresa", example = "400")
    Integer horasEmpresa,

    @Schema(description = "ID del profesor que imparte el curso", example = "1")
    Long profesorId,

    @Schema(description = "ID del título al que pertenece el curso", example = "1")
    Long tituloId
) {}