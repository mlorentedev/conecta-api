package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateFamiliaProfesionalDTO;
import com.conecta.model.FamiliaProfesional;
import com.conecta.repository.FamiliaProfesionalRepository;
import com.conecta.exception.CustomException;

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
        try {
            return familiaProfesionalRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar familias profesionales");
        }
    }

    public Optional<FamiliaProfesional> findById(Long id) {
        if (!familiaProfesionalRepository.existsById(id)) {
            throw new CustomException("Familia no encontrada");
        }
        try {
            return familiaProfesionalRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar familia profesional");
        }
    }

    public FamiliaProfesional create(CreateUpdateFamiliaProfesionalDTO familiaDTO) {
        try {
            FamiliaProfesional familia = new FamiliaProfesional();
            return updateFamiliaFromDTO(familia, familiaDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear familia profesional");
        }
    }

    public Optional<FamiliaProfesional> update(Long id, CreateUpdateFamiliaProfesionalDTO familiaDTO) {
        if (!familiaProfesionalRepository.existsById(id)) {
            throw new CustomException("Familia no encontrada");
        }
        try {
            return familiaProfesionalRepository.findById(id)
                    .map(familia -> updateFamiliaFromDTO(familia, familiaDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar familia profesional");
        }
    }

    public Boolean delete(Long id) {
        try {
            FamiliaProfesional familia = familiaProfesionalRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Familia no encontrada"));
            familia.getTitulos().clear();
            familia.getEmpresas().clear();
            familiaProfesionalRepository.delete(familia);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar familia profesional");
        }
    }

    private FamiliaProfesional updateFamiliaFromDTO(FamiliaProfesional familia, CreateUpdateFamiliaProfesionalDTO familiaDTO) {        
        familia.setNombre(familiaDTO.nombre());
        return familiaProfesionalRepository.save(familia);
    }
}
