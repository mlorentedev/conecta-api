package com.conecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.FamiliaProfesional;

@Repository
public interface FamiliaProfesionalRepository extends JpaRepository<FamiliaProfesional, Long> {
}
