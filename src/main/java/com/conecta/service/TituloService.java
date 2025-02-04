package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return tituloRepository.findById(id);
    }

    public Titulo create(TituloDTO tituloDTO) {
        Titulo titulo = new Titulo();
        return updateTituloFromDTO(titulo, tituloDTO);
    }

    public Optional<Titulo> update(Long id, TituloDTO tituloDTO) {
        return tituloRepository.findById(id)
                .map(titulo -> updateTituloFromDTO(titulo, tituloDTO));
    }

    public void delete(Long id) {
        tituloRepository.deleteById(id);
    }

    private Titulo updateTituloFromDTO(Titulo titulo, TituloDTO tituloDTO) {
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