package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateConvocatoriaDTO;
import com.conecta.model.Convocatoria;
import com.conecta.repository.ConvocatoriaRepository;

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
        return convocatoriaRepository.findAll();
    }

    public Optional<Convocatoria> findById(Long id) {
        return convocatoriaRepository.findById(id);
    }

    public Convocatoria create(CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        Convocatoria convocatoria = new Convocatoria();
        return updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO);
    }

    public Optional<Convocatoria> update(Long id, CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        return convocatoriaRepository.findById(id)
                .map(convocatoria -> updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO));
    }

    public Boolean delete(Long id) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));
        convocatoria.getDemandas().clear();
        convocatoriaRepository.deleteById(id);
        return true;
    }

    private Convocatoria updateConvocatoriaFromDTO(Convocatoria convocatoria, CreateUpdateConvocatoriaDTO convocatoriaDTO) {
        convocatoria.setCursoEscolar(convocatoriaDTO.cursoEscolar());
        convocatoria.setNombre(convocatoriaDTO.nombre());
        return convocatoriaRepository.save(convocatoria);
    }

}
