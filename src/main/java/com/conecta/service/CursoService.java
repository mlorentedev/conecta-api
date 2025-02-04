package com.conecta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conecta.repository.CursoRepository;
import com.conecta.repository.ProfesorRepository;
import com.conecta.repository.TituloRepository;

import jakarta.transaction.Transactional;

import com.conecta.dto.CursoDTO;
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
        return cursoRepository.findById(id);
    }

    public Curso create(CursoDTO cursoDTO) {
        Curso curso = new Curso();
        return updateCursoFromDTO(curso, cursoDTO);
    }

    public Optional<Curso> update(Long id, CursoDTO cursoDTO) {
        return cursoRepository.findById(id)
                .map(curso -> updateCursoFromDTO(curso, cursoDTO));
    }

    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }

    private Curso updateCursoFromDTO(Curso curso, CursoDTO cursoDTO) {
        curso.setNombre(cursoDTO.nombre());
        curso.setHorasEmpresa(cursoDTO.horasEmpresa());
        profesorRepository.findById(cursoDTO.profesorId())
                .ifPresent(curso::setProfesor);
        tituloRepository.findById(cursoDTO.tituloId())
                .ifPresent(curso::setTitulo);
        return cursoRepository.save(curso);
    }
}