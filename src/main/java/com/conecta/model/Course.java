package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;

import java.util.Set;


@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String level;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfessionalFamily family;
    
    @ManyToMany(mappedBy = "courses")
    private Set<Teacher> teachers;
    
    @OneToMany(mappedBy = "course")
    private Set<Demand> demands;
}