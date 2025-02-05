package com.conecta.dto;

import com.conecta.model.Convocatoria;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una convocatoria")
public record CreateUpdateConvocatoriaDTO(
    @Schema(description = "Curso escolar de la convocatoria", example = "2023-2024")
    String cursoEscolar,

    @Schema(description = "Nombre de la convocatoria", example = "Convocatoria Ordinaria DAW")
    String nombre
) {

    public static CreateUpdateConvocatoriaDTO fromEntity(Convocatoria convocatoria) {
        return new CreateUpdateConvocatoriaDTO(
            convocatoria.getCursoEscolar(),
            convocatoria.getNombre()
        );
    }
}