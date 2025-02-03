package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Entity
public class Convocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String schoolYear;
    
    @OneToMany(mappedBy = "convocation")
    private Set<Demand> demands;
}
