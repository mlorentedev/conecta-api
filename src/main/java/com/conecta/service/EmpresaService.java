package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateEmpresaDTO;
import com.conecta.model.Empresa;
import com.conecta.repository.EmpresaRepository;
import com.conecta.exception.CustomException;

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
        try {
            return empresaRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar empresas");
        }
    }

    public Optional<Empresa> findById(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            return empresaRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar empresa");
        }
    }

    public Empresa create(CreateUpdateEmpresaDTO empresaDTO) {
        try {
            Empresa empresa = new Empresa();
            return updateEmpresaFromDTO(empresa, empresaDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear empresa");
        }
    }

    public Optional<Empresa> update(Long id, CreateUpdateEmpresaDTO empresaDTO) {
        if (!empresaRepository.existsById(id)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            return empresaRepository.findById(id)
                    .map(empresa -> updateEmpresaFromDTO(empresa, empresaDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar empresa");
        }
    }

    public Boolean delete(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            empresaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar empresa");
        }
    }

    private Empresa updateEmpresaFromDTO(Empresa empresa, CreateUpdateEmpresaDTO empresaDTO) {
        empresa.setCif(empresaDTO.cif());
        empresa.setDireccion(empresaDTO.direccion());
        empresa.setCoordenadas(empresaDTO.coordenadas());
        empresa.setNombre(empresaDTO.nombre());
        return empresaRepository.save(empresa);
    }

}