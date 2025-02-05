package com.conecta.dto;

import com.conecta.model.Titulo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un título")
public record CreateUpdateTituloDTO(

    @Schema(description = "Nombre del título", example = "Desarrollo de Aplicaciones Web")
    String nombre,

    @Schema(description = "Duración del título", example = "2000 horas")
    String duracion,

    @Schema(description = "Grado del título", example = "Superior")
    String grado,

    @Schema(description = "ID de la familia profesional", example = "1")
    Long familiaProfesionalId
) {

    public static CreateUpdateTituloDTO fromEntity(Titulo titulo) {
        return new CreateUpdateTituloDTO(
                titulo.getNombre(),
                titulo.getDuracion(),
                titulo.getGrado(),
                titulo.getFamiliaProfesional().getId()
        );
    }

}