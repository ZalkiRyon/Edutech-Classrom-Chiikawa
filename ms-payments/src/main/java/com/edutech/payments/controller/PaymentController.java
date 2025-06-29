package com.edutech.payments.controller;

import com.edutech.common.dto.PaymentDTO;
import com.edutech.payments.service.PaymentService;
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
@RequestMapping("/api/payments")
@Tag(name = "Pagos", description = "API para gestión de pagos y transacciones")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Retorna una lista de todos los pagos")
    public ResponseEntity<CollectionModel<EntityModel<PaymentDTO>>> findAll() {
        List<EntityModel<PaymentDTO>> payments = paymentService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<PaymentDTO>> collectionModel = CollectionModel.of(payments)
                .add(linkTo(methodOn(PaymentController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago específico por su ID")
    public ResponseEntity<EntityModel<PaymentDTO>> findById(@PathVariable Integer id) {
        PaymentDTO payment = paymentService.findById(id);
        return ResponseEntity.ok(toEntityModel(payment));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener pagos por usuario", description = "Retorna todos los pagos de un usuario específico")
    public ResponseEntity<CollectionModel<EntityModel<PaymentDTO>>> findByUserId(@PathVariable Integer userId) {
        List<EntityModel<PaymentDTO>> payments = paymentService.findByUserId(userId).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<PaymentDTO>> collectionModel = CollectionModel.of(payments)
                .add(linkTo(methodOn(PaymentController.class).findByUserId(userId)).withSelfRel())
                .add(linkTo(methodOn(PaymentController.class).findAll()).withRel("all-payments"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pago", description = "Procesa un nuevo pago")
    public ResponseEntity<EntityModel<PaymentDTO>> create(@RequestBody PaymentDTO dto) {
        PaymentDTO created = paymentService.create(dto);
        return ResponseEntity.ok(toEntityModel(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago", description = "Actualiza un pago existente")
    public ResponseEntity<EntityModel<PaymentDTO>> update(@PathVariable Integer id, @RequestBody PaymentDTO dto) {
        PaymentDTO updated = paymentService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private EntityModel<PaymentDTO> toEntityModel(PaymentDTO payment) {
        return EntityModel.of(payment)
                .add(linkTo(methodOn(PaymentController.class).findById(payment.getId())).withSelfRel())
                .add(linkTo(methodOn(PaymentController.class).findByUserId(payment.getUserId())).withRel("user-payments"))
                .add(linkTo(methodOn(PaymentController.class).findAll()).withRel("all-payments"));
    }
}
