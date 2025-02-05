package com.conecta.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "convocatoria")
public class Convocatoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cursoEscolar;
    private String nombre;
    
    @OneToMany(mappedBy = "convocatoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demanda> demandas;

    public void addDemanda(Demanda demanda) {
        demandas.add(demanda);
        demanda.setConvocatoria(this);
    }

    public void removeDemanda(Demanda demanda) {
        demandas.remove(demanda);
        demanda.setConvocatoria(null);
    }

}
