package com.conecta.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "profesor")
public class Profesor extends Persona {
@ManyToMany
    @JoinTable(
        name = "profesor_contacto_trabajador",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "trabajador_id")
    )
    private Set<Trabajador> trabajadoresContacto;
}