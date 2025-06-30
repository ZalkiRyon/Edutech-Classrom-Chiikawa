package com.edutech.users.controller;

import com.edutech.common.dto.RoleDTO;
import com.edutech.users.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "API para gestión de roles del sistema")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Retorna una lista de todos los roles con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<RoleDTO>>> findAll() {
        List<RoleDTO> roles = roleService.findAll();
        
        List<EntityModel<RoleDTO>> roleModels = roles.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<RoleDTO>> collectionModel = CollectionModel.of(roleModels);
        collectionModel.add(linkTo(RoleController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Retorna un rol específico por su ID con enlaces HATEOAS")
    public ResponseEntity<EntityModel<RoleDTO>> findById(@PathVariable Integer id) {
        RoleDTO role = roleService.findById(id);
        EntityModel<RoleDTO> roleModel = addLinksToDto(role);
        return ResponseEntity.ok(roleModel);
    }

    @PostMapping
    @Operation(summary = "Crear rol", description = "Crea un nuevo rol y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<RoleDTO>> create(@RequestBody @Valid RoleDTO dto) {
        RoleDTO createdRole = roleService.create(dto);
        EntityModel<RoleDTO> roleModel = addLinksToDto(createdRole);
        return ResponseEntity.ok(roleModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza un rol existente y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<RoleDTO>> update(@PathVariable Integer id, @RequestBody @Valid RoleDTO dto) {
        RoleDTO updatedRole = roleService.update(id, dto);
        EntityModel<RoleDTO> roleModel = addLinksToDto(updatedRole);
        return ResponseEntity.ok(roleModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<RoleDTO> addLinksToDto(RoleDTO role) {
        EntityModel<RoleDTO> roleModel = EntityModel.of(role);
        
        roleModel.add(linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel());
        roleModel.add(linkTo(RoleController.class).withRel("roles"));
        roleModel.add(linkTo(methodOn(RoleController.class).update(role.getId(), role)).withRel("update"));
        roleModel.add(linkTo(methodOn(RoleController.class).delete(role.getId())).withRel("delete"));
        
        return roleModel;
    }
}
