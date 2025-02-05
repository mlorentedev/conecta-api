package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateContactoDTO;
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
        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException("Contacto no encontrado");
        }
        return contactoRepository.findById(id);
    }

    public Contacto create(CreateUpdateContactoDTO contactoDTO) {
        Contacto contacto = new Contacto();
        return updateContactoFromDTO(contacto, contactoDTO);
    }

    public Optional<Contacto> update(Long id, CreateUpdateContactoDTO contactoDTO) {
        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException("Contacto no encontrado");
        }
        return contactoRepository.findById(id)
                .map(contacto -> updateContactoFromDTO(contacto, contactoDTO));
    }

    public Boolean delete(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException("Contacto no encontrado");
        }
        contactoRepository.deleteById(id);
        return true;
    }

    private Contacto updateContactoFromDTO(Contacto contacto, CreateUpdateContactoDTO contactoDTO) {
        contacto.setFecha(contactoDTO.fecha());
        contacto.setCanal(contactoDTO.canal());
        contacto.setResumen(contactoDTO.resumen());

        Empresa empresa = empresaRepository.findById(contactoDTO.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        contacto.setEmpresa(empresa);

        return contactoRepository.save(contacto);
    }

}