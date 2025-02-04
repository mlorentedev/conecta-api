package com.conecta.dto;

import java.util.List;

import com.conecta.model.Demanda;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una convocatoria")
public record ConvocatoriaDTO(
    @Schema(description = "ID de la convocatoria", example = "1")
    Long id,

    @Schema(description = "Curso escolar de la convocatoria", example = "2023-2024")
    String cursoEscolar,

    @Schema(description = "Nombre de la convocatoria", example = "Convocatoria Ordinaria DAW")
    String nombre,

    @Schema(description = "Lista de demandas asociadas a la convocatoria", example = "Convocatoria Ordinaria DAW")
    List<Demanda> demandas
) {}