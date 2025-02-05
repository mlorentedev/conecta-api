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

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DemandaService {

    private final DemandaRepository demandaRepository;
    private final EmpresaRepository empresaRepository;
    private final CursoRepository cursoRepository;

    public DemandaService(DemandaRepository demandaRepository, 
                          EmpresaRepository empresaRepository, 
                          CursoRepository cursoRepository) {
        this.demandaRepository = demandaRepository;
        this.empresaRepository = empresaRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Demanda> findAll() {
        return demandaRepository.findAll();
    }

    public Optional<Demanda> findById(Long id) {
        if (!demandaRepository.existsById(id)) {
            throw new RuntimeException("Demanda no encontrada");
        }
        return demandaRepository.findById(id);
    }

    public Demanda create(CreateUpdateDemandaDTO demandaDTO) {
        Demanda demanda = new Demanda();
        return updateDemandaFromDTO(demanda, demandaDTO);
    }

    public Optional<Demanda> update(Long id, CreateUpdateDemandaDTO demandaDTO) {
        return demandaRepository.findById(id)
                .map(demanda -> updateDemandaFromDTO(demanda, demandaDTO));
    }

    public Boolean delete(Long id) {
        Demanda demanda = demandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demanda no encontrada"));
        demandaRepository.delete(demanda);
        return true;
    }

    private Demanda updateDemandaFromDTO(Demanda demanda, CreateUpdateDemandaDTO demandaDTO) {
        demanda.setCantidadAlumnos(demandaDTO.cantidadAlumnos());
        demanda.setRequisitos(demandaDTO.requisitos());

        Empresa empresa = empresaRepository.findById(demandaDTO.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        demanda.setEmpresa(empresa);

        Curso curso = cursoRepository.findById(demandaDTO.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        demanda.setCurso(curso);

        return demandaRepository.save(demanda);
    }

    public List<Demanda> findByEmpresaId(Long empresaId) {
        return demandaRepository.findByEmpresaId(empresaId);
    }

    public List<Demanda> findByCursoId(Long cursoId) {
        return demandaRepository.findByCursoId(cursoId);
    }
}