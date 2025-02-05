package com.conecta.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "familia_profesional")
public class FamiliaProfesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @OneToMany(mappedBy = "familiaProfesional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Titulo> titulos = new HashSet<>();

    @ManyToMany(mappedBy = "familiasProfesionales")
    private Set<Empresa> empresas = new HashSet<>();

    public void addTitulo(Titulo titulo) {
        titulos.add(titulo);
        titulo.setFamiliaProfesional(this);
    }

    public void removeTitulo(Titulo titulo) {
        titulos.remove(titulo);
        titulo.setFamiliaProfesional(null);
    }

}
