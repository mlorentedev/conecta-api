package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateTituloDTO;
import com.conecta.model.FamiliaProfesional;
import com.conecta.model.Titulo;
import com.conecta.repository.FamiliaProfesionalRepository;
import com.conecta.repository.TituloRepository;
import com.conecta.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TituloService {

    private final TituloRepository tituloRepository;
    private final FamiliaProfesionalRepository familiaProfesionalRepository;

    public TituloService(TituloRepository tituloRepository, FamiliaProfesionalRepository familiaProfesionalRepository) {
        this.tituloRepository = tituloRepository;
        this.familiaProfesionalRepository = familiaProfesionalRepository;
    }

    public List<Titulo> findAll() {
        try {
            return tituloRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al obtener los títulos");
        }
    }

    public Optional<Titulo> findById(Long id) {
        if (!tituloRepository.existsById(id)) {
            throw new CustomException("Título no encontrado");
        }
        try {
            return tituloRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al obtener el título");
        }
    }

    public Titulo create(CreateUpdateTituloDTO tituloDTO) {
        try {
        Titulo titulo = new Titulo();
        return updateTituloFromDTO(titulo, tituloDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear el título");
        }
    }

    public Optional<Titulo> update(Long id, CreateUpdateTituloDTO tituloDTO) {
        if (!tituloRepository.existsById(id)) {
            throw new CustomException("Título no encontrado");
        }
        try {
            return tituloRepository.findById(id)
                    .map(titulo -> updateTituloFromDTO(titulo, tituloDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar el título");
        }
    }

    public Boolean delete(Long id) {
        if (!tituloRepository.existsById(id)) {
            throw new CustomException("Título no encontrado");
        }
        try {
            Titulo titulo = tituloRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Título no encontrado"));
            tituloRepository.delete(titulo);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar el título");
        }
    }

    private Titulo updateTituloFromDTO(Titulo titulo, CreateUpdateTituloDTO tituloDTO) {
        titulo.setNombre(tituloDTO.nombre());
        titulo.setDuracion(tituloDTO.duracion());
        titulo.setGrado(tituloDTO.grado());

        FamiliaProfesional familiaProfesional = familiaProfesionalRepository.findById(tituloDTO.familiaProfesionalId())
                .orElseThrow(() -> new CustomException("Familia Profesional no encontrada"));
        titulo.setFamiliaProfesional(familiaProfesional);

        return tituloRepository.save(titulo);
    }

    public List<Titulo> findByFamiliaProfesionalId(Long familiaProfesionalId) {
        if (!familiaProfesionalRepository.existsById(familiaProfesionalId)) {
            throw new CustomException("Familia Profesional no encontrada");
        }
        try {
            return tituloRepository.findByFamiliaProfesionalId(familiaProfesionalId);
        } catch (Exception e) {
            throw new CustomException("Error al obtener los títulos de la familia profesional");
        }
    }
}