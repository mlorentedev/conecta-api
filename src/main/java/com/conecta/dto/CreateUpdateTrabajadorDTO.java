package com.conecta.dto;

import com.conecta.model.Trabajador;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un trabajador")
public record CreateUpdateTrabajadorDTO(
    
    @Schema(description = "Nombre del trabajador", example = "Ana")
    String nombre,

    @Schema(description = "Apellidos del trabajador", example = "Martín Díaz")
    String apellidos,

    @Schema(description = "Email del trabajador", example = "ana.martin@techsolutions.com")
    String email,

    @Schema(description = "Teléfono del trabajador", example = "666777888")
    String telefono,

    @Schema(description = "Puesto del trabajador", example = "Director RRHH")
    String puesto,

    @Schema(description = "Área del trabajador", example = "Recursos Humanos")
    String area,

    @Schema(description = "ID de la empresa del trabajador", example = "1")
    Long empresaId
) {

    public static CreateUpdateTrabajadorDTO fromEntity(Trabajador trabajador) {
        return new CreateUpdateTrabajadorDTO(
            trabajador.getNombre(),
            trabajador.getApellidos(),
            trabajador.getEmail(),
            trabajador.getTelefono(),
            trabajador.getPuesto(),
            trabajador.getArea(),
            trabajador.getEmpresa().getId()
        );
    }
}
