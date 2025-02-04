package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.EmpresaDTO;
import com.conecta.dto.FamiliaProfesionalDTO;
import com.conecta.exception.ResourceNotFoundException;
import com.conecta.model.Empresa;
import com.conecta.model.FamiliaProfesional;
import com.conecta.model.Trabajador;
import com.conecta.repository.EmpresaRepository;
import com.conecta.repository.FamiliaProfesionalRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final FamiliaProfesionalRepository familiaProfesionalRepository;

    public EmpresaService(EmpresaRepository empresaRepository, FamiliaProfesionalRepository familiaProfesionalRepository) {
        this.empresaRepository = empresaRepository;
        this.familiaProfesionalRepository = familiaProfesionalRepository;
    }
    
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa create(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        return updateEmpresaFromDTO(empresa, empresaDTO);
    }

    public Optional<Empresa> update(Long id, EmpresaDTO empresaDTO) {
        return empresaRepository.findById(id)
                .map(empresa -> updateEmpresaFromDTO(empresa, empresaDTO));
    }

    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }

    private Empresa updateEmpresaFromDTO(Empresa empresa, EmpresaDTO empresaDTO) {
        empresa.setCif(empresaDTO.cif());
        empresa.setDireccion(empresaDTO.direccion());
        empresa.setCoordenadas(empresaDTO.coordenadas());
        empresa.setNombre(empresaDTO.nombre());
        return empresaRepository.save(empresa);
    }

    @SuppressWarnings("unchecked")
    public List<Trabajador> findTrabajadoresByEmpresaId(Long empresaId) {
        return (List<Trabajador>) empresaRepository.findById(empresaId)
                .map(Empresa::getTrabajadores)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }
public void addFamiliaProfesional(Long empresaId, Long familiaProfesionalId) throws ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        FamiliaProfesional familiaProfesional = familiaProfesionalRepository.findById(familiaProfesionalId)
            .orElseThrow(() -> new ResourceNotFoundException("Familia Profesional no encontrada"));
        
        empresa.getFamiliasProfesionales().add(familiaProfesional);
        empresaRepository.save(empresa);
    }

    public void removeFamiliaProfesional(Long empresaId, Long familiaProfesionalId) throws ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        empresa.getFamiliasProfesionales().removeIf(fp -> fp.getId().equals(familiaProfesionalId));
        empresaRepository.save(empresa);
    }

    public Set<FamiliaProfesionalDTO> getFamiliasProfesionales(Long empresaId) throws ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        return empresa.getFamiliasProfesionales().stream()
            .map(this::convertFamiliaProfesionalToDTO)
            .collect(Collectors.toSet());
    }

    private FamiliaProfesionalDTO convertFamiliaProfesionalToDTO(FamiliaProfesional familiaProfesional) {
        return new FamiliaProfesionalDTO(familiaProfesional.getId(), familiaProfesional.getNombre());
    }

}