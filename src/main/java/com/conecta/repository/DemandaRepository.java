package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Demanda;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    List<Demanda> findByEmpresaId(Long empresaId);
    List<Demanda> findByCursoId(Long cursoId);
}
