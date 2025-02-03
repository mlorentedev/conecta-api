package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Convocation convocation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    
    private Integer numberOfStudents;
    private String requirements;
}
