package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateFamiliaProfesionalDTO;
import com.conecta.model.FamiliaProfesional;
import com.conecta.repository.FamiliaProfesionalRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FamiliaProfesionalService {

    private final FamiliaProfesionalRepository familiaProfesionalRepository;

    public FamiliaProfesionalService(FamiliaProfesionalRepository familiaProfesionalRepository) {
        this.familiaProfesionalRepository = familiaProfesionalRepository;
    }

    public List<FamiliaProfesional> findAll() {
        return familiaProfesionalRepository.findAll();
    }

    public Optional<FamiliaProfesional> findById(Long id) {
        return familiaProfesionalRepository.findById(id);
    }

    public FamiliaProfesional create(CreateUpdateFamiliaProfesionalDTO familiaDTO) {
        FamiliaProfesional familia = new FamiliaProfesional();
        return updateFamiliaFromDTO(familia, familiaDTO);
    }

    public Optional<FamiliaProfesional> update(Long id, CreateUpdateFamiliaProfesionalDTO familiaDTO) {
        return familiaProfesionalRepository.findById(id)
                .map(familia -> updateFamiliaFromDTO(familia, familiaDTO));
    }

    public Boolean delete(Long id) {
        FamiliaProfesional familia = familiaProfesionalRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Familia no encontrada"));
        familia.getTitulos().clear();
        familia.getEmpresas().clear();
        familiaProfesionalRepository.delete(familia);
        return true;
    }

    private FamiliaProfesional updateFamiliaFromDTO(FamiliaProfesional familia, CreateUpdateFamiliaProfesionalDTO familiaDTO) {        
        familia.setNombre(familiaDTO.nombre());
        return familiaProfesionalRepository.save(familia);
    }
}
