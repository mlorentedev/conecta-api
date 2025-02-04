package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.ContactoDTO;
import com.conecta.model.Contacto;
import com.conecta.model.Empresa;
import com.conecta.repository.ContactoRepository;
import com.conecta.repository.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final EmpresaRepository empresaRepository;

    public ContactoService(ContactoRepository contactoRepository, EmpresaRepository empresaRepository) {
        this.contactoRepository = contactoRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<Contacto> findAll() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> findById(Long id) {
        return contactoRepository.findById(id);
    }

    public Contacto create(ContactoDTO contactoDTO) {
        Contacto contacto = new Contacto();
        return updateContactoFromDTO(contacto, contactoDTO);
    }

    public Optional<Contacto> update(Long id, ContactoDTO contactoDTO) {
        return contactoRepository.findById(id)
                .map(contacto -> updateContactoFromDTO(contacto, contactoDTO));
    }

    public void delete(Long id) {
        contactoRepository.deleteById(id);
    }

    private Contacto updateContactoFromDTO(Contacto contacto, ContactoDTO contactoDTO) {
        contacto.setFecha(contactoDTO.fecha());
        contacto.setCanal(contactoDTO.canal());
        contacto.setResumen(contactoDTO.resumen());

        Empresa empresa = empresaRepository.findById(contactoDTO.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        contacto.setEmpresa(empresa);

        return contactoRepository.save(contacto);
    }

    public List<Contacto> findByEmpresaId(Long empresaId) {
        return contactoRepository.findByEmpresaId(empresaId);
    }
}