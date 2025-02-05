package com.conecta.dto;

import com.conecta.model.Empresa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una empresa")
public record CreateUpdateEmpresaDTO(

    @Schema(description = "CIF de la empresa", example = "B12345678")
    String cif,

    @Schema(description = "Dirección de la empresa", example = "Calle Principal 123, Madrid")
    String direccion,

    @Schema(description = "Coordenadas de la empresa", example = "40.4168,-3.7038")
    String coordenadas,

    @Schema(description = "Nombre de la empresa", example = "TechSolutions S.L.")
    String nombre
) {

    public static CreateUpdateEmpresaDTO fromEntity(Empresa empresa) {
        return new CreateUpdateEmpresaDTO(
            empresa.getCif(),
            empresa.getDireccion(),
            empresa.getCoordenadas(),
            empresa.getNombre()
        );
    }
}
