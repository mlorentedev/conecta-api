package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cif;
    private String direccion;
    private String coordenadas;
    private String nombre;
    
    @OneToMany(mappedBy = "empresa", orphanRemoval = true)
    private Set<Trabajador> trabajadores = new HashSet<>();
    
    @OneToMany(mappedBy = "empresa", orphanRemoval = true)
    private Set<Contacto> contactos = new HashSet<>();
    
    @OneToMany(mappedBy = "empresa", orphanRemoval = true)
    private Set<Demanda> demandas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "empresa_familia_profesional",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "familia_profesional_id")
    )
    private Set<FamiliaProfesional> familiasProfesionales = new HashSet<>();

}