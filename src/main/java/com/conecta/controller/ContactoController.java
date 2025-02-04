package com.conecta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conecta.dto.ContactoDTO;
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
    @ApiResponse(responseCode = "200", description = "Lista de contactos obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ContactoDTO.class)))
    public ResponseEntity<List<ContactoDTO>> getAllContactos() {
        List<ContactoDTO> contactosDTO = contactoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un contacto por ID", description = "Retorna un contacto basado en su ID")
    @ApiResponse(responseCode = "200", description = "Contacto encontrado", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ContactoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    public ResponseEntity<ContactoDTO> getContactoById(
            @Parameter(description = "ID del contacto a buscar", required = true)
            @PathVariable Long id) {
        return contactoService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo contacto", description = "Crea un nuevo contacto y retorna los datos creados")
    @ApiResponse(responseCode = "200", description = "Contacto creado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ContactoDTO.class)))
    public ResponseEntity<ContactoDTO> createContacto(
            @Parameter(description = "Datos del contacto a crear", required = true)
            @RequestBody ContactoDTO contactoDTO) {
        Contacto createdContacto = contactoService.create(contactoDTO);
        return ResponseEntity.ok(convertToDTO(createdContacto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un contacto existente", description = "Actualiza un contacto existente basado en su ID")
    @ApiResponse(responseCode = "200", description = "Contacto actualizado con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ContactoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    public ResponseEntity<ContactoDTO> updateContacto(
            @Parameter(description = "ID del contacto a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevos datos del contacto", required = true)
            @RequestBody ContactoDTO contactoDTO) {
        return contactoService.update(id, contactoDTO)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un contacto", description = "Elimina un contacto basado en su ID")
    @ApiResponse(responseCode = "204", description = "Contacto eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Contacto no encontrado")
    public ResponseEntity<Void> deleteContacto(
            @Parameter(description = "ID del contacto a eliminar", required = true)
            @PathVariable Long id) {
        contactoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener contactos por empresa", description = "Retorna una lista de contactos asociados a una empresa")
    @ApiResponse(responseCode = "200", description = "Lista de contactos obtenida con éxito", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = ContactoDTO.class)))
    public ResponseEntity<List<ContactoDTO>> getContactosByEmpresa(
            @Parameter(description = "ID de la empresa", required = true)
            @PathVariable Long empresaId) {
        List<ContactoDTO> contactosDTO = contactoService.findByEmpresaId(empresaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactosDTO);
    }

    private ContactoDTO convertToDTO(Contacto contacto) {
        return new ContactoDTO(
            contacto.getId(),
            contacto.getFecha(),
            contacto.getCanal(),
            contacto.getResumen(),
            contacto.getEmpresa().getId()
        );
    }
}
