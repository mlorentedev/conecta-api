package com.conecta.dto;

import com.conecta.model.Demanda;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una demanda")
public record CreateUpdateDemandaDTO(

    @Schema(description = "Cantidad de alumnos requeridos", example = "5")
    Integer cantidadAlumnos,

    @Schema(description = "Requisitos de la demanda", example = "Conocimientos de Java y Spring")
    String requisitos,

    @Schema(description = "ID de la empresa asociada", example = "1")
    Long empresaId,

    @Schema(description = "ID del curso asociado", example = "1")
    Long cursoId
) {
    
    public static CreateUpdateDemandaDTO fromEntity(Demanda demanda) {
        return new CreateUpdateDemandaDTO(
                demanda.getCantidadAlumnos(),
                demanda.getRequisitos(),
                demanda.getEmpresa().getId(),
                demanda.getCurso().getId()
        );
    }
}
