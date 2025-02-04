package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.FamiliaProfesionalDTO;
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

    public FamiliaProfesional create(FamiliaProfesionalDTO familiaProfesionalDTO) {
        FamiliaProfesional familiaProfesional = new FamiliaProfesional();
        return updateFamiliaProfesionalFromDTO(familiaProfesional, familiaProfesionalDTO);
    }

    public Optional<FamiliaProfesional> update(Long id, FamiliaProfesionalDTO familiaProfesionalDTO) {
        return familiaProfesionalRepository.findById(id)
                .map(familiaProfesional -> updateFamiliaProfesionalFromDTO(familiaProfesional, familiaProfesionalDTO));
    }

    public void delete(Long id) {
        familiaProfesionalRepository.deleteById(id);
    }

    private FamiliaProfesional updateFamiliaProfesionalFromDTO(FamiliaProfesional familiaProfesional, FamiliaProfesionalDTO familiaProfesionalDTO) {
        familiaProfesional.setNombre(familiaProfesionalDTO.nombre());
        return familiaProfesionalRepository.save(familiaProfesional);
    }
}
