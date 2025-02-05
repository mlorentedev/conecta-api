package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.conecta.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    @Query("SELECT c FROM Contacto c WHERE c.empresa.id = :empresaId")
    List<Contacto> findByEmpresaId(@Param("empresaId") Long empresaId);

    @Query("SELECT DISTINCT c FROM Contacto c " +
           "JOIN c.empresa e " +
           "JOIN e.familiasProfesionales fp " +
           "WHERE fp.id = :familiaId")
    List<Contacto> findByFamiliaId(@Param("familiaId") Long familiaId);

    @Query("SELECT c FROM Contacto c " +
    "JOIN c.empresa e " +
    "JOIN e.trabajadores t " +
    "JOIN t.profesoresContacto p " +
    "WHERE p.id = :profesorId")
    List<Contacto> findByProfesorId(@Param("profesorId") Long profesorId);

    @Query("SELECT c FROM Contacto c " +
    "WHERE c.empresa.id = :empresaId " +
    "AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Contacto> findByEmpresaIdAndFecha(
        @Param("empresaId") Long empresaId, 
        @Param("fechaInicio") String fechaInicio, 
        @Param("fechaFin") String fechaFin);
}