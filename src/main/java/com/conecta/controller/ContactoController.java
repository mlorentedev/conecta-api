package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.ContactoDTO;
import com.conecta.dto.CreateUpdateContactoDTO;
import com.conecta.model.Contacto;
import com.conecta.service.ContactoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los contactos", description = "Retorna una lista de todos los contactos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contactos encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ContactoDTO>> getAllContactos() {
        List<ContactoDTO> contactosDTO = contactoService.findAll().stream()
                .map(ContactoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un contacto por ID", description = "Retorna un contacto basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacto encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contacto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ContactoDTO> getContactoById(
            @Parameter(description = "ID del contacto a buscar", required = true)
            @PathVariable("id") Long id) {
        ContactoDTO contacto = ContactoDTO.fromEntity(contactoService.findById(id).get());
        return ResponseEntity.ok(contacto);
    }

    @PostMapping
    @Operation(summary = "Crear un contacto", description = "Crea un nuevo contacto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacto creado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ContactoDTO> createContacto(
            @Parameter(description = "Datos del contacto a crear", required = true)
            @RequestBody CreateUpdateContactoDTO contactoDTO) {
        Contacto contacto = contactoService.create(contactoDTO);
        return ResponseEntity.ok(ContactoDTO.fromEntity(contacto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un contacto", description = "Actualiza un contacto basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacto actualizado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contacto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ContactoDTO> updateContacto(
            @Parameter(description = "ID del contacto a actualizar", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Datos del contacto a actualizar", required = true)
            @RequestBody CreateUpdateContactoDTO contactoDTO) {
        Contacto contacto = contactoService.update(id, contactoDTO).get();
        return ResponseEntity.ok(ContactoDTO.fromEntity(contacto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un contacto", description = "Elimina un contacto basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacto eliminado"),
        @ApiResponse(responseCode = "404", description = "Contacto no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> deleteContacto(
            @Parameter(description = "ID del contacto a eliminar", required = true)
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(contactoService.delete(id));
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener contactos por ID de empresa", description = "Retorna una lista de contactos basados en el ID de la empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contactos encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ContactoDTO>> getContactosByEmpresaId(
            @Parameter(description = "ID de la empresa a buscar", required = true)
            @PathVariable("empresaId") Long empresaId) {
        List<ContactoDTO> contactosDTO = contactoService.findByEmpresaId(empresaId).stream()
                .map(ContactoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

    @GetMapping("/familia-profesional/{familiaId}")
    @Operation(summary = "Obtener contactos por ID de familia profesional", description = "Retorna una lista de contactos basados en el ID de la familia profesional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contactos encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ContactoDTO>> getContactosByFamiliaId(
            @Parameter(description = "ID de la familia profesional a buscar", required = true)
            @PathVariable("familiaId") Long familiaId) {
        List<ContactoDTO> contactosDTO = contactoService.findByFamiliaId(familiaId).stream()
                .map(ContactoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

    @GetMapping("/profesor/{profesorId}")
    @Operation(summary = "Obtener contactos por ID de profesor", description = "Retorna una lista de contactos basados en el ID del profesor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contactos encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ContactoDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ContactoDTO>> getContactosByProfesorId(
            @Parameter(description = "ID del profesor a buscar", required = true)
            @PathVariable("profesorId") Long profesorId) {
        List<ContactoDTO> contactosDTO = contactoService.findByProfesorId(profesorId).stream()
                .map(ContactoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

}
