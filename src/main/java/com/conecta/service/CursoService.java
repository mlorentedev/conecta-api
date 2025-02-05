package com.conecta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conecta.repository.CursoRepository;
import com.conecta.repository.ProfesorRepository;
import com.conecta.repository.TituloRepository;

import jakarta.transaction.Transactional;

import com.conecta.dto.CreateUpdateCursoDTO;
import com.conecta.model.Curso;
import com.conecta.model.Profesor;
import com.conecta.model.Titulo;


@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ProfesorRepository profesorRepository;
    private final TituloRepository tituloRepository;

    public CursoService(CursoRepository cursoRepository, ProfesorRepository profesorRepository, TituloRepository tituloRepository) {
        this.cursoRepository = cursoRepository;
        this.profesorRepository = profesorRepository;
        this.tituloRepository = tituloRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> findById(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado");
        }
        return cursoRepository.findById(id);
    }

    public Curso create(CreateUpdateCursoDTO cursoDTO) {
        Curso curso = new Curso();
        return updateCursoFromDTO(curso, cursoDTO);
    }

    public Optional<Curso> update(Long id, CreateUpdateCursoDTO cursoDTO) {
        return cursoRepository.findById(id)
                .map(curso -> updateCursoFromDTO(curso, cursoDTO));
    }

    public Boolean delete(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.getDemandas().clear();
        cursoRepository.delete(curso);
        return true;
    }

    private Curso updateCursoFromDTO(Curso curso, CreateUpdateCursoDTO cursoDTO) {
        curso.setNombre(cursoDTO.nombre());
        curso.setHorasEmpresa(cursoDTO.horasEmpresa());
        if (cursoDTO.profesorId() != null) {
            Profesor profesor = profesorRepository.findById(cursoDTO.profesorId())
                    .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            curso.setProfesor(profesor);
        }
        if (cursoDTO.tituloId() != null) {
            Titulo titulo = tituloRepository.findById(cursoDTO.tituloId())
                    .orElseThrow(() -> new RuntimeException("Titulo no encontrado"));
            curso.setTitulo(titulo);
        }
        return cursoRepository.save(curso);
    }
}