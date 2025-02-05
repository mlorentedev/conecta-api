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
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado");
        }
        if (profesorRepository.existsById(cursoDTO.profesorId())) {
            throw new RuntimeException("Profesor no encontrado");
        }
        if (tituloRepository.existsById(cursoDTO.tituloId())) {
            throw new RuntimeException("Título no encontrado");
        }
        return cursoRepository.findById(id)
                .map(curso -> updateCursoFromDTO(curso, cursoDTO));
    }

    public Boolean delete(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado");
        }
        cursoRepository.deleteById(id);
        return true;
    }

    private Curso updateCursoFromDTO(Curso curso, CreateUpdateCursoDTO cursoDTO) {
        curso.setNombre(cursoDTO.nombre());
        curso.setHorasEmpresa(cursoDTO.horasEmpresa());
        profesorRepository.findById(cursoDTO.profesorId())
                .ifPresent(curso::setProfesor);
        tituloRepository.findById(cursoDTO.tituloId())
                .ifPresent(curso::setTitulo);
        return cursoRepository.save(curso);
    }
}