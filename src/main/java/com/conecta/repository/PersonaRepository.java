package com.conecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
