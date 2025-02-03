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
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    @ManyToMany
    private Set<Course> courses;
    
    @OneToMany(mappedBy = "teacher")
    private Set<Communication> communications;
}