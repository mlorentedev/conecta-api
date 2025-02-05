package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateProfesorDTO;
import com.conecta.model.Profesor;
import com.conecta.repository.ProfesorRepository;
import com.conecta.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    public List<Profesor> findAll() {
        try {
            return profesorRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar profesores");
        }
    }

    public Optional<Profesor> findById(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            return profesorRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar profesor");
        }
    }

    public Profesor create(CreateUpdateProfesorDTO profesorDTO) {
        try {
            Profesor profesor = new Profesor();
            return updateProfesorFromDTO(profesor, profesorDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear profesor");
        }
    }

    public Optional<Profesor> update(Long id, CreateUpdateProfesorDTO profesorDTO) {
        if (!profesorRepository.existsById(id)) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            return profesorRepository.findById(id)
                    .map(profesor -> updateProfesorFromDTO(profesor, profesorDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar profesor");
        }
    }

    public Boolean delete(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            profesorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar profesor");
        }
    }

    private Profesor updateProfesorFromDTO(Profesor profesor, CreateUpdateProfesorDTO profesorDTO) {
        profesor.setNombre(profesorDTO.nombre());
        profesor.setApellidos(profesorDTO.apellidos());
        profesor.setEmail(profesorDTO.email());
        profesor.setTelefono(profesorDTO.telefono());
        return profesorRepository.save(profesor);
    }
}