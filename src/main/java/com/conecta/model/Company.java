package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import lombok.Data;

import java.util.Set;


@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Employee> employees;
    
    @ManyToMany
    @JoinTable(name = "company_professional_family",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_family_id"))
    private Set<ProfessionalFamily> professionalFamilies;
    
    @OneToMany(mappedBy = "company")
    private Set<Demand> demands;
}