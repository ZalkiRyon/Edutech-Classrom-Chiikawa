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
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all users with HATEOAS links")
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
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by its ID with HATEOAS links")
    public ResponseEntity<EntityModel<UserDTO>> findById(@PathVariable Integer id) {
        UserDTO user = userService.findById(id);
        EntityModel<UserDTO> userModel = addLinksToDto(user);
        return ResponseEntity.ok(userModel);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user and return it with HATEOAS links")
    public ResponseEntity<EntityModel<UserDTO>> create(@RequestBody UserDTO dto) {
        UserDTO createdUser = userService.create(dto);
        EntityModel<UserDTO> userModel = addLinksToDto(createdUser);
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user and return it with HATEOAS links")
    public ResponseEntity<EntityModel<UserDTO>> update(@PathVariable Integer id, @RequestBody UserDTO dto) {
        UserDTO updatedUser = userService.update(id, dto);
        EntityModel<UserDTO> userModel = addLinksToDto(updatedUser);
        return ResponseEntity.ok(userModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by its ID")
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
