package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Integer horasEmpresa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titulo_id")
    private Titulo titulo;
    
    @OneToMany(mappedBy = "curso", orphanRemoval = true)
    private Set<Demanda> demandas = new HashSet<>();

    public void addDemanda(Demanda demanda) {
        demandas.add(demanda);
        demanda.setCurso(this);
    }

    public void removeDemanda(Demanda demanda) {
        demandas.remove(demanda);
        demanda.setCurso(null);
    }
}