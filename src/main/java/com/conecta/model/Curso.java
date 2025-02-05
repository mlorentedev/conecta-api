package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Integer horasEmpresa;
    
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
    
    @ManyToOne
    @JoinColumn(name = "titulo_id")
    private Titulo titulo;
    
    @OneToMany(mappedBy = "curso")
    private Set<Demanda> demandas = new HashSet<>();
}