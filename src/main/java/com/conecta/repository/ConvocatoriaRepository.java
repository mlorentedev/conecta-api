package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Convocatoria;


@Repository
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {
    List<Convocatoria> findByCursoEscolar(String cursoEscolar);
}
