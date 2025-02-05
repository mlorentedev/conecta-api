package com.conecta.dto;

import com.conecta.model.FamiliaProfesional;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una familia profesional")
public record CreateUpdateFamiliaProfesionalDTO(

    @Schema(description = "Nombre de la familia profesional", example = "Informática y Comunicaciones")
    String nombre
) {
    
    public static CreateUpdateFamiliaProfesionalDTO fromEntity(FamiliaProfesional familiaProfesional) {
        return new CreateUpdateFamiliaProfesionalDTO(
            familiaProfesional.getNombre()
        );
    }
}