package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUpdateContactoDTO;
import com.conecta.exception.CustomException;
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
        try {
            return contactoRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos");
        }
    }

    public Optional<Contacto> findById(Long id) {
        try {
            return contactoRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contacto");
        }
    }

    public Contacto create(CreateUpdateContactoDTO contactoDTO) {
        try {
            Contacto contacto = new Contacto();
            return updateContactoFromDTO(contacto, contactoDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear contacto");
        }
    }

    public Optional<Contacto> update(Long id, CreateUpdateContactoDTO contactoDTO) {
        if (!contactoRepository.existsById(id)) {
            throw new CustomException("Contacto no encontrado");
        }
        try {
            return contactoRepository.findById(id)
                    .map(contacto -> updateContactoFromDTO(contacto, contactoDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar contacto");
        }
    }

    public Boolean delete(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new CustomException("Contacto no encontrado");
        }
        try {
            contactoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar contacto");
        }
    }

    public List<Contacto> findByEmpresaId(Long empresaId) {
        if (!empresaRepository.existsById(empresaId)) {
            throw new CustomException("Empresa no encontrada");
        }
        try {
            return contactoRepository.findByEmpresaId(empresaId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos de la empresa");
        }
    }

    public List<Contacto> findByFamiliaId(Long familiaId) {
        if (!empresaRepository.existsById(familiaId)) {
            throw new CustomException("Familia no encontrada");
        }
        try {
            return contactoRepository.findByFamiliaId(familiaId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos de la familia");
        }
    }

    public List<Contacto> findByProfesorId(Long profesorId) {
        if (!empresaRepository.existsById(profesorId)) {
            throw new CustomException("Profesor no encontrado");
        }
        try {
            return contactoRepository.findByProfesorId(profesorId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos del profesor");
        }
    }

    private Contacto updateContactoFromDTO(Contacto contacto, CreateUpdateContactoDTO contactoDTO) {
        contacto.setFecha(contactoDTO.fecha());
        contacto.setCanal(contactoDTO.canal());
        contacto.setResumen(contactoDTO.resumen());
        Empresa empresa = empresaRepository.findById(contactoDTO.empresaId()).get();
        contacto.setEmpresa(empresa);
        return contactoRepository.save(contacto);
    }

}