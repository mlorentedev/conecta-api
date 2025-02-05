package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateDemandaDTO;
import com.conecta.model.Curso;
import com.conecta.model.Demanda;
import com.conecta.model.Empresa;
import com.conecta.repository.CursoRepository;
import com.conecta.repository.DemandaRepository;
import com.conecta.repository.EmpresaRepository;
import com.conecta.repository.FamiliaProfesionalRepository;
import com.conecta.repository.ProfesorRepository;
import com.conecta.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DemandaService {

    private final DemandaRepository demandaRepository;
    private final EmpresaRepository empresaRepository;
    private final CursoRepository cursoRepository;
    private final FamiliaProfesionalRepository familiaRepository;
    private final ProfesorRepository profesorRepository;

    public DemandaService(DemandaRepository demandaRepository, 
                          EmpresaRepository empresaRepository, 
                          CursoRepository cursoRepository,
                          FamiliaProfesionalRepository familiaRepository,
                          ProfesorRepository profesorRepository) {
        this.demandaRepository = demandaRepository;
        this.empresaRepository = empresaRepository;
        this.cursoRepository = cursoRepository;
        this.familiaRepository = familiaRepository;
        this.profesorRepository = profesorRepository;
    }

    public List<Demanda> findAll() {
        try {
            return demandaRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas");
        }
    }

    public Optional<Demanda> findById(Long id) {
        if (!demandaRepository.existsById(id)) {
            throw new CustomException("Demanda no encontrada");
        }
        try {
            return demandaRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demanda");
        }
    }

    public Demanda create(CreateUpdateDemandaDTO demandaDTO) {
        if (demandaDTO.empresaId() == null) {
            throw new CustomException("Empresa no puede ser nula");
        }
        try {
            Demanda demanda = new Demanda();
            return updateDemandaFromDTO(demanda, demandaDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear demanda");
        }
    }

    public Optional<Demanda> update(Long id, CreateUpdateDemandaDTO demandaDTO) {
        if (!demandaRepository.existsById(id)) {
            throw new CustomException("Demanda no encontrada");
        }
        try {
            return demandaRepository.findById(id)
                    .map(demanda -> updateDemandaFromDTO(demanda, demandaDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar demanda");
        }
    }

    public Boolean delete(Long id) {
        try {
            Demanda demanda = demandaRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Demanda no encontrada"));
            demandaRepository.delete(demanda);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar demanda");
        }
    }

    private Demanda updateDemandaFromDTO(Demanda demanda, CreateUpdateDemandaDTO demandaDTO) {
        demanda.setCantidadAlumnos(demandaDTO.cantidadAlumnos());
        demanda.setRequisitos(demandaDTO.requisitos());

        Empresa empresa = empresaRepository.findById(demandaDTO.empresaId())
                .orElseThrow(() -> new CustomException("Empresa no encontrada"));
        demanda.setEmpresa(empresa);

        Curso curso = cursoRepository.findById(demandaDTO.cursoId())
                .orElseThrow(() -> new CustomException("Curso no encontrado"));
        demanda.setCurso(curso);

        return demandaRepository.save(demanda);
    }

    public List<Demanda> findByEmpresaId(Long empresaId) {
        if (!empresaRepository.existsById(empresaId)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            return demandaRepository.findByEmpresaId(empresaId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas de empresa");
        }
    }

    public List<Demanda> findByCursoId(Long cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new CustomException("Curso no encontrado");
        }
        try {
            return demandaRepository.findByCursoId(cursoId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas de curso");
        }
    }

    public List<Demanda> findByEmpresaIdAndCursoEscolar(Long empresaId, String cursoEscolar) {
        if (!empresaRepository.existsById(empresaId)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            return demandaRepository.findByEmpresaIdAndCursoEscolar(empresaId, cursoEscolar);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas de empresa y curso escolar");
        }
    }

    public List<Demanda> findByFamiliaId(Long familiaId) {
        if (!familiaRepository.existsById(familiaId)) {
            throw new CustomException("Familia no encontrada");
        }
        try {
            return demandaRepository.findByFamiliaId(familiaId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas de familia");
        }
    }

    public List<Demanda> findByProfesorId(Long profesorId) {
        if (!profesorRepository.existsById(profesorId)) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            return demandaRepository.findByProfesorId(profesorId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar demandas de profesor");
        }
    }

}