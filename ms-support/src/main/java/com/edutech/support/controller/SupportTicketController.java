package com.edutech.support.controller;

import com.edutech.common.dto.SupportTicketDTO;
import com.edutech.support.service.SupportTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/support-tickets")
@Tag(name = "Tickets de Soporte", description = "API para gestión de tickets de soporte técnico")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los tickets", description = "Retorna una lista de todos los tickets de soporte")
    public ResponseEntity<CollectionModel<EntityModel<SupportTicketDTO>>> findAll() {
        List<EntityModel<SupportTicketDTO>> tickets = supportTicketService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<SupportTicketDTO>> collectionModel = CollectionModel.of(tickets)
                .add(linkTo(methodOn(SupportTicketController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ticket por ID", description = "Retorna un ticket específico por su ID")
    public ResponseEntity<EntityModel<SupportTicketDTO>> findById(@PathVariable Integer id) {
        SupportTicketDTO ticket = supportTicketService.findById(id);
        return ResponseEntity.ok(toEntityModel(ticket));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener tickets por usuario", description = "Retorna todos los tickets creados por un usuario específico")
    public ResponseEntity<CollectionModel<EntityModel<SupportTicketDTO>>> findByUserId(@PathVariable Integer userId) {
        List<EntityModel<SupportTicketDTO>> tickets = supportTicketService.findByUserId(userId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<SupportTicketDTO>> collectionModel = CollectionModel.of(tickets)
                .add(linkTo(methodOn(SupportTicketController.class).findByUserId(userId)).withSelfRel())
                .add(linkTo(methodOn(SupportTicketController.class).findAll()).withRel("all-tickets"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/support-user/{supportUserId}")
    @Operation(summary = "Obtener tickets por usuario de soporte", description = "Retorna todos los tickets asignados a un usuario de soporte")
    public ResponseEntity<CollectionModel<EntityModel<SupportTicketDTO>>> findBySupportUserId(@PathVariable Integer supportUserId) {
        List<EntityModel<SupportTicketDTO>> tickets = supportTicketService.findBySupportUserId(supportUserId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<SupportTicketDTO>> collectionModel = CollectionModel.of(tickets)
                .add(linkTo(methodOn(SupportTicketController.class).findBySupportUserId(supportUserId)).withSelfRel())
                .add(linkTo(methodOn(SupportTicketController.class).findAll()).withRel("all-tickets"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo ticket", description = "Crea un nuevo ticket de soporte")
    public ResponseEntity<EntityModel<SupportTicketDTO>> create(@RequestBody SupportTicketDTO dto) {
        SupportTicketDTO created = supportTicketService.create(dto);
        return ResponseEntity.ok(toEntityModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ticket", description = "Actualiza un ticket de soporte existente")
    public ResponseEntity<EntityModel<SupportTicketDTO>> update(@PathVariable Integer id, @RequestBody SupportTicketDTO dto) {
        SupportTicketDTO updated = supportTicketService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ticket", description = "Elimina un ticket de soporte por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        supportTicketService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private EntityModel<SupportTicketDTO> toEntityModel(SupportTicketDTO ticket) {
        EntityModel<SupportTicketDTO> entityModel = EntityModel.of(ticket)
                .add(linkTo(methodOn(SupportTicketController.class).findById(ticket.getId())).withSelfRel())
                .add(linkTo(methodOn(SupportTicketController.class).findByUserId(ticket.getUserId())).withRel("user-tickets"))
                .add(linkTo(methodOn(SupportTicketController.class).findAll()).withRel("all-tickets"));
        
        // Si tiene usuario de soporte asignado, agregar enlace
        if (ticket.getSupportUserId() != null) {
            entityModel.add(linkTo(methodOn(SupportTicketController.class).findBySupportUserId(ticket.getSupportUserId())).withRel("support-user-tickets"));
        }
        
        return entityModel;
    }
}
