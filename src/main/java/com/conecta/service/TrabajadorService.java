package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateTrabajadorDTO;
import com.conecta.model.Empresa;
import com.conecta.model.Trabajador;
import com.conecta.repository.EmpresaRepository;
import com.conecta.repository.TrabajadorRepository;
import com.conecta.exception.CustomException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final EmpresaRepository empresaRepository;

    public TrabajadorService(TrabajadorRepository trabajadorRepository, EmpresaRepository empresaRepository) {
        this.trabajadorRepository = trabajadorRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<Trabajador> findAll() {
        try {
            return trabajadorRepository.findAll();
        } 
        catch (Exception e) {
            throw new CustomException("Error al buscar trabajadores");
        }
    }

    public Optional<Trabajador> findById(Long id) {
        if (!trabajadorRepository.existsById(id)) {
            throw new CustomException("Trabajador no encontrado");
        }
        try {
            return trabajadorRepository.findById(id);
        } 
        catch (Exception e) {
            throw new CustomException("Error al buscar trabajador");
        }
    }

    public List<Trabajador> findByEmpresaId(Long empresaId) {
        try {
            return trabajadorRepository.findByEmpresaId(empresaId);
        } 
        catch (Exception e) {
            throw new CustomException("Error al buscar trabajadores de la empresa "+empresaId);
        }
    }

    public Trabajador create(CreateUpdateTrabajadorDTO trabajadorDTO) {
        try {
            Trabajador trabajador = new Trabajador();
            return updateTrabajadorFromDTO(trabajador, trabajadorDTO);
        } 
        catch (Exception e) {
            throw new CustomException("Error al crear trabajador");
        }
    }

    public Optional<Trabajador> update(Long id, CreateUpdateTrabajadorDTO trabajadorDTO) {
        if (!trabajadorRepository.existsById(id)) {
            throw new CustomException("Trabajador no encontrado");
        }
        try {
            return trabajadorRepository.findById(id)
                    .map(trabajador -> updateTrabajadorFromDTO(trabajador, trabajadorDTO));
        } 
        catch (Exception e) {
            throw new CustomException("Error al actualizar trabajador");
        }
    }

    public Boolean delete(Long id) {
        if (!trabajadorRepository.existsById(id)) {
            throw new CustomException("Trabajador no encontrado");
        }
        try {
            trabajadorRepository.deleteById(id);
            return true;
        } 
        catch (Exception e) {
            throw new CustomException("Error al eliminar trabajador");
        }
    }

    private Trabajador updateTrabajadorFromDTO(Trabajador trabajador, CreateUpdateTrabajadorDTO trabajadorDTO) {
            trabajador.setNombre(trabajadorDTO.nombre());
            trabajador.setApellidos(trabajadorDTO.apellidos());
            trabajador.setEmail(trabajadorDTO.email());
            trabajador.setTelefono(trabajadorDTO.telefono());
            trabajador.setPuesto(trabajadorDTO.puesto());
            trabajador.setArea(trabajadorDTO.area());

            Empresa empresa = empresaRepository.findById(trabajadorDTO.empresaId())
                    .orElseThrow(() -> new CustomException("Empresa no encontrada"));
            trabajador.setEmpresa(empresa);

            return trabajadorRepository.save(trabajador);
    }

}
