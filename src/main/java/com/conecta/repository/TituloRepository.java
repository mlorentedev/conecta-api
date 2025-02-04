package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {
    List<Titulo> findByFamiliaProfesionalId(Long familiaProfesionalId);
}