package com.conecta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conecta.repository.CursoRepository;
import com.conecta.repository.ProfesorRepository;
import com.conecta.repository.TituloRepository;
import com.conecta.exception.CustomException;

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
        try {
            return cursoRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar cursos");
        }
    }

    public Optional<Curso> findById(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new CustomException("Curso no encontrado");
        }
        try {
            return cursoRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar curso");
        }
    }

    public Curso create(CreateUpdateCursoDTO cursoDTO) {
        if (cursoDTO.profesorId() == null) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            Curso curso = new Curso();
            return updateCursoFromDTO(curso, cursoDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear curso");
        }
    }

    public Optional<Curso> update(Long id, CreateUpdateCursoDTO cursoDTO) {
        if (!cursoRepository.existsById(id)) {
            throw new CustomException("Curso no encontrado");
        }
        try {
            return cursoRepository.findById(id)
                    .map(curso -> updateCursoFromDTO(curso, cursoDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar curso");
        }
    }

    public Boolean delete(Long id) {
        try {
            Curso curso = cursoRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Curso no encontrado"));
            curso.getDemandas().clear();
            cursoRepository.delete(curso);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar curso");
        }
    }

    private Curso updateCursoFromDTO(Curso curso, CreateUpdateCursoDTO cursoDTO) {
        curso.setNombre(cursoDTO.nombre());
        curso.setHorasEmpresa(cursoDTO.horasEmpresa());
        if (cursoDTO.profesorId() != null) {
            Profesor profesor = profesorRepository.findById(cursoDTO.profesorId())
                    .orElseThrow(() -> new CustomException("Profesor no encontrado"));
            curso.setProfesor(profesor);
        }
        if (cursoDTO.tituloId() != null) {
            Titulo titulo = tituloRepository.findById(cursoDTO.tituloId())
                    .orElseThrow(() -> new CustomException("Titulo no encontrado"));
            curso.setTitulo(titulo);
        }
        return cursoRepository.save(curso);
    }
}