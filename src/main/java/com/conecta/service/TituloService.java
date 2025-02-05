package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateTituloDTO;
import com.conecta.dto.TituloDTO;
import com.conecta.model.FamiliaProfesional;
import com.conecta.model.Titulo;
import com.conecta.repository.FamiliaProfesionalRepository;
import com.conecta.repository.TituloRepository;

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
        return tituloRepository.findAll();
    }

    public Optional<Titulo> findById(Long id) {
        if (!tituloRepository.existsById(id)) {
            throw new RuntimeException("Título no encontrado");
        }
        return tituloRepository.findById(id);
    }

    public Titulo create(CreateUpdateTituloDTO tituloDTO) {
        Titulo titulo = new Titulo();
        return updateTituloFromDTO(titulo, tituloDTO);
    }

    public Optional<Titulo> update(Long id, CreateUpdateTituloDTO tituloDTO) {
        return tituloRepository.findById(id)
                .map(titulo -> updateTituloFromDTO(titulo, tituloDTO));
    }

    public Boolean delete(Long id) {
        Titulo titulo = tituloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Título no encontrado"));
        tituloRepository.delete(titulo);
        return true;
    }

    private Titulo updateTituloFromDTO(Titulo titulo, CreateUpdateTituloDTO tituloDTO) {
        titulo.setNombre(tituloDTO.nombre());
        titulo.setDuracion(tituloDTO.duracion());
        titulo.setGrado(tituloDTO.grado());

        FamiliaProfesional familiaProfesional = familiaProfesionalRepository.findById(tituloDTO.familiaProfesionalId())
                .orElseThrow(() -> new RuntimeException("Familia Profesional no encontrada"));
        titulo.setFamiliaProfesional(familiaProfesional);

        return tituloRepository.save(titulo);
    }

    public List<Titulo> findByFamiliaProfesionalId(Long familiaProfesionalId) {
        return tituloRepository.findByFamiliaProfesionalId(familiaProfesionalId);
    }
}