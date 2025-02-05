package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateProfesorDTO;
import com.conecta.model.Profesor;
import com.conecta.repository.ProfesorRepository;

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
        return profesorRepository.findAll();
    }

    public Optional<Profesor> findById(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new RuntimeException("Profesor no encontrado");
        }
        return profesorRepository.findById(id);
    }

    public Profesor create(CreateUpdateProfesorDTO profesorDTO) {
        Profesor profesor = new Profesor();
        return updateProfesorFromDTO(profesor, profesorDTO);
    }

    public Optional<Profesor> update(Long id, CreateUpdateProfesorDTO profesorDTO) {
        if (!profesorRepository.existsById(id)) {
            throw new RuntimeException("Profesor no encontrado");
        }
        return profesorRepository.findById(id)
                .map(profesor -> updateProfesorFromDTO(profesor, profesorDTO));
    }

    public Boolean delete(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new RuntimeException("Profesor no encontrado");
        }
        profesorRepository.deleteById(id);
        return true;
    }

    private Profesor updateProfesorFromDTO(Profesor profesor, CreateUpdateProfesorDTO profesorDTO) {
        profesor.setNombre(profesorDTO.nombre());
        profesor.setApellidos(profesorDTO.apellidos());
        profesor.setEmail(profesorDTO.email());
        profesor.setTelefono(profesorDTO.telefono());
        return profesorRepository.save(profesor);
    }
}