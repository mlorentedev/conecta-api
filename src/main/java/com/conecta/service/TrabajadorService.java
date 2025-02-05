package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.TrabajadorDTO;
import com.conecta.model.Empresa;
import com.conecta.model.Trabajador;
import com.conecta.repository.EmpresaRepository;
import com.conecta.repository.TrabajadorRepository;

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
        return trabajadorRepository.findAll();
    }

    public Optional<Trabajador> findById(Long id) {
        if (!trabajadorRepository.existsById(id)) {
            throw new RuntimeException("Trabajador no encontrado");
        }
        return trabajadorRepository.findById(id);
    }

    public List<Trabajador> findByEmpresaId(Long empresaId) {
        return trabajadorRepository.findByEmpresaId(empresaId);
    }

    public Trabajador create(TrabajadorDTO trabajadorDTO) {
        Trabajador trabajador = new Trabajador();
        return updateTrabajadorFromDTO(trabajador, trabajadorDTO);
    }

    public Optional<Trabajador> update(Long id, TrabajadorDTO trabajadorDTO) {
        if (!trabajadorRepository.existsById(id)) {
            throw new RuntimeException("Trabajador no encontrado");
        }
        return trabajadorRepository.findById(id)
                .map(trabajador -> updateTrabajadorFromDTO(trabajador, trabajadorDTO));
    }

    public Boolean delete(Long id) {
        if (!trabajadorRepository.existsById(id)) {
            throw new RuntimeException("Trabajador no encontrado");
        }
        trabajadorRepository.deleteById(id);
        return true;
    }

    private Trabajador updateTrabajadorFromDTO(Trabajador trabajador, TrabajadorDTO trabajadorDTO) {
        trabajador.setNombre(trabajadorDTO.nombre());
        trabajador.setApellidos(trabajadorDTO.apellidos());
        trabajador.setEmail(trabajadorDTO.email());
        trabajador.setTelefono(trabajadorDTO.telefono());
        trabajador.setPuesto(trabajadorDTO.puesto());
        trabajador.setArea(trabajadorDTO.area());

        Empresa empresa = empresaRepository.findById(trabajadorDTO.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        trabajador.setEmpresa(empresa);

        return trabajadorRepository.save(trabajador);
    }

}
