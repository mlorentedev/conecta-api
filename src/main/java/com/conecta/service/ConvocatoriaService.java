package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateConvocatoriaDTO;
import com.conecta.model.Convocatoria;
import com.conecta.repository.ConvocatoriaRepository;
import com.conecta.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConvocatoriaService {

    private final ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaService(ConvocatoriaRepository convocatoriaRepository) {
        this.convocatoriaRepository = convocatoriaRepository;
    }

    public List<Convocatoria> findAll() {
        try {
            return convocatoriaRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar convocatorias");
        }
    }

    public Optional<Convocatoria> findById(Long id) {
        if (!convocatoriaRepository.existsById(id)) {
            throw new CustomException("Convocatoria no encontrada");
        }
        try {
            return convocatoriaRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar convocatoria");
        }
    }

    public Convocatoria create(CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        try {
            Convocatoria convocatoria = new Convocatoria();
            return updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear convocatoria");
        }
    }

    public Optional<Convocatoria> update(Long id, CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        if (!convocatoriaRepository.existsById(id)) {
            throw new CustomException("Convocatoria no encontrada");
        }
        try {
            return convocatoriaRepository.findById(id)
                    .map(convocatoria -> updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar convocatoria");
        }
    }

    public Boolean delete(Long id) {
        if (!convocatoriaRepository.existsById(id)) {
            throw new CustomException("Convocatoria no encontrada");
        }
        try {
            Convocatoria convocatoria = convocatoriaRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Convocatoria no encontrada"));
            convocatoria.getDemandas().clear();
            convocatoriaRepository.delete(convocatoria);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar la convocatoria");
        }
    }

    private Convocatoria updateConvocatoriaFromDTO(Convocatoria convocatoria, CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        convocatoria.setCursoEscolar(convocatoriaDTO.cursoEscolar());
        convocatoria.setNombre(convocatoriaDTO.nombre());
        return convocatoriaRepository.save(convocatoria);
    }

}
