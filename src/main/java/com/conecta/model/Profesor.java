package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "profesor")
public class Profesor extends Persona {
@ManyToMany
    @JoinTable(
        name = "profesor_trabajador",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "trabajador_id")
    )
    private Set<Trabajador> trabajadoresContacto;
}