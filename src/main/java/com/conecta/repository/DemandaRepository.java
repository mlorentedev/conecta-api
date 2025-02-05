package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.conecta.model.Demanda;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {

    @Query("SELECT d FROM Demanda d WHERE d.empresa.id = :empresaId")
    List<Demanda> findByEmpresaId(@Param("empresaId") Long empresaId);

    @Query("SELECT d FROM Demanda d " +
           "JOIN d.curso c " +
           "WHERE c.id = :cursoId")
    List<Demanda> findByCursoId(Long cursoId);

    @Query("SELECT d FROM Demanda d " +
    "WHERE d.empresa.id = :empresaId " +
    "AND d.convocatoria.cursoEscolar = :cursoEscolar")
    List<Demanda> findByEmpresaIdAndCursoEscolar(
        @Param("empresaId") Long empresaId, 
        @Param("cursoEscolar") String cursoEscolar);

    @Query("SELECT d FROM Demanda d " +
    "JOIN d.curso c " +
    "JOIN c.titulo t " +
    "WHERE t.familiaProfesional.id = :familiaId")
    List<Demanda> findByFamiliaId(@Param("familiaId") Long familiaId);

    @Query("SELECT d FROM Demanda d " +
           "JOIN d.curso c " +
           "WHERE c.profesor.id = :profesorId")
    List<Demanda> findByProfesorId(@Param("profesorId") Long profesorId);
}
