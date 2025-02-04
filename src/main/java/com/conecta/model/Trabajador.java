package com.conecta.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "trabajador")
public class Trabajador extends Persona {
    private String puesto;
    private String area;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToMany(mappedBy = "trabajadoresContacto")
    private Set<Profesor> profesoresContacto;

}
