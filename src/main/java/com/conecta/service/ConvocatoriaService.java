package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.ConvocatoriaDTO;
import com.conecta.model.Convocatoria;
import com.conecta.model.Demanda;
import com.conecta.repository.ConvocatoriaRepository;
import com.conecta.repository.DemandaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConvocatoriaService {

    private final ConvocatoriaRepository convocatoriaRepository;
    private final DemandaRepository demandaRepository;

    public ConvocatoriaService(ConvocatoriaRepository convocatoriaRepository, DemandaRepository demandaRepository) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.demandaRepository = demandaRepository;
    }

    public List<Convocatoria> findAll() {
        return convocatoriaRepository.findAll();
    }

    public Optional<Convocatoria> findById(Long id) {
        return convocatoriaRepository.findById(id);
    }

    public Convocatoria create(ConvocatoriaDTO convocatoriaDTO) {
        Convocatoria convocatoria = new Convocatoria();
        return updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO);
    }

    public Optional<Convocatoria> update(Long id, ConvocatoriaDTO convocatoriaDTO) {
        return convocatoriaRepository.findById(id)
                .map(convocatoria -> updateConvocatoriaFromDTO(convocatoria, convocatoriaDTO));
    }

    public void delete(Long id) {
        convocatoriaRepository.deleteById(id);
    }

    private Convocatoria updateConvocatoriaFromDTO(Convocatoria convocatoria, ConvocatoriaDTO convocatoriaDTO) {
        convocatoria.setCursoEscolar(convocatoriaDTO.cursoEscolar());
        convocatoria.setNombre(convocatoriaDTO.nombre());

        List<Demanda> demandas = convocatoriaDTO.demandas();
        convocatoria.getDemandas().clear();
        demandas.forEach(demanda -> {
            demanda.setConvocatoria(convocatoria);
            demandaRepository.save(demanda);
        });

        return convocatoriaRepository.save(convocatoria);
    }

}
