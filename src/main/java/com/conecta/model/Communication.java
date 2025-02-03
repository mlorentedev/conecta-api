package com.conecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
}
