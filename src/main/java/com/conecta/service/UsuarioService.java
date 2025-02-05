package com.conecta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conecta.dto.CreateUsuarioDTO;
import com.conecta.dto.UpdateUsuarioDTO;
import com.conecta.exception.CustomException;
import com.conecta.model.Profesor;
import com.conecta.model.Usuario;
import com.conecta.repository.ProfesorRepository;
import com.conecta.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProfesorRepository profesorRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ProfesorRepository profesorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
    }

    public List<Usuario> findAll() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar usuarios");
        }
    }

    public Optional<Usuario> findById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            return usuarioRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar usuario");
        }
     }

    public Usuario create(CreateUsuarioDTO usuarioDTO) {
        try {
            Profesor nuevoProfesor = profesorRepository.save(new Profesor());
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioDTO.username());
            usuario.setPassword(usuarioDTO.password());
            usuario.setRole(usuarioDTO.role());
            usuario.setProfesor(nuevoProfesor);
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new CustomException("Error al crear usuario");
        }
    }

    public Optional<Usuario> update(Long id, UpdateUsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            Profesor profesor = usuario.getProfesor();
            usuario.setUsername(usuarioDTO.username());
            usuario.setPassword(usuarioDTO.password());
            usuario.setRole(usuarioDTO.role());
            usuario.setProfesor(profesor);
            usuarioRepository.save(usuario);
            return Optional.of(usuario);
        } catch (Exception e) {
            throw new CustomException("Error al actualizar usuario");
        }

    }

    public Boolean delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar usuario");
        }
    }

}