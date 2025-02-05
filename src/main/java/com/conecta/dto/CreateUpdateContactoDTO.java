package com.conecta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

import com.conecta.model.Contacto;

@Schema(description = "Datos de un contacto")
public record CreateUpdateContactoDTO(

    @Schema(description = "Fecha del contacto", example = "2024-01-15")
    LocalDate fecha,

    @Schema(description = "Canal de contacto", example = "Email")
    String canal,

    @Schema(description = "Resumen del contacto", example = "Solicitud información prácticas")
    String resumen,

    @Schema(description = "ID de la empresa asociada", example = "1")
    Long empresaId
) {

    public static CreateUpdateContactoDTO fromEntity(Contacto contacto) {
        return new CreateUpdateContactoDTO(
                contacto.getFecha(),
                contacto.getCanal(),
                contacto.getResumen(),
                contacto.getEmpresa().getId()
        );
    }
}
