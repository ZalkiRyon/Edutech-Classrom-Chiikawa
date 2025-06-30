package com.edutech.users.controller;

import com.edutech.common.dto.UserDTO;
import com.edutech.users.service.*;

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
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para gestión de usuarios del sistema")
public class UserController {

    private final UserService userService;

    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> findAll() {
        List<UserDTO> users = userService.findAll();
        
        List<EntityModel<UserDTO>> userModels = users.stream()
                .map(this::addLinksToDto)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<UserDTO>> collectionModel = CollectionModel.of(userModels);
        collectionModel.add(linkTo(UserController.class).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID con enlaces HATEOAS")
    public ResponseEntity<EntityModel<UserDTO>> findById(@PathVariable Integer id) {
        UserDTO user = userService.findById(id);
        EntityModel<UserDTO> userModel = addLinksToDto(user);
        return ResponseEntity.ok(userModel);
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<UserDTO>> create(@RequestBody UserDTO dto) {
        UserDTO createdUser = userService.create(dto);
        EntityModel<UserDTO> userModel = addLinksToDto(createdUser);
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente y lo retorna con enlaces HATEOAS")
    public ResponseEntity<EntityModel<UserDTO>> update(@PathVariable Integer id, @RequestBody UserDTO dto) {
        UserDTO updatedUser = userService.update(id, dto);
        EntityModel<UserDTO> userModel = addLinksToDto(updatedUser);
        return ResponseEntity.ok(userModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<UserDTO> addLinksToDto(UserDTO user) {
        EntityModel<UserDTO> userModel = EntityModel.of(user);
        
        userModel.add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel());
        userModel.add(linkTo(UserController.class).withRel("users"));
        userModel.add(linkTo(methodOn(UserController.class).update(user.getId(), user)).withRel("update"));
        userModel.add(linkTo(methodOn(UserController.class).delete(user.getId())).withRel("delete"));
        
        return userModel;
    }
}
