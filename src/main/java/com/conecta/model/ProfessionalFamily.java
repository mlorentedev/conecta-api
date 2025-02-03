package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import lombok.Data;

import java.util.Set;


@Data
@Entity
public class ProfessionalFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    
    @OneToMany(mappedBy = "family")
    private Set<Course> courses;
    
    @ManyToMany(mappedBy = "professionalFamilies")
    private Set<Company> companies;

}
