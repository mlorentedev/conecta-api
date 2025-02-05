package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateEmpresaDTO;
import com.conecta.model.Empresa;
import com.conecta.repository.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }
    
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa create(CreateUpdateEmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        return updateEmpresaFromDTO(empresa, empresaDTO);
    }

    public Optional<Empresa> update(Long id, CreateUpdateEmpresaDTO empresaDTO) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        return empresaRepository.findById(id)
                .map(empresa -> updateEmpresaFromDTO(empresa, empresaDTO));
    }

    public Boolean delete(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        empresaRepository.deleteById(id);
        return true;
    }

    private Empresa updateEmpresaFromDTO(Empresa empresa, CreateUpdateEmpresaDTO empresaDTO) {
        empresa.setCif(empresaDTO.cif());
        empresa.setDireccion(empresaDTO.direccion());
        empresa.setCoordenadas(empresaDTO.coordenadas());
        empresa.setNombre(empresaDTO.nombre());
        return empresaRepository.save(empresa);
    }

}