package com.edutech.payments.controller;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.service.DiscountCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/discount-coupons")
@Tag(name = "Cupones de Descuento", description = "API para gestión de cupones de descuento y promociones")
public class DiscountCouponController {

    private final DiscountCouponService discountCouponService;

    public DiscountCouponController(DiscountCouponService discountCouponService) {
        this.discountCouponService = discountCouponService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cupones", description = "Retorna una lista de todos los cupones de descuento")
    public ResponseEntity<CollectionModel<EntityModel<DiscountCouponDTO>>> findAll() {
        List<EntityModel<DiscountCouponDTO>> coupons = discountCouponService.findAll().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<DiscountCouponDTO>> collectionModel = CollectionModel.of(coupons)
                .add(linkTo(methodOn(DiscountCouponController.class).findAll()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cupón por ID", description = "Retorna un cupón específico por su ID")
    public ResponseEntity<EntityModel<DiscountCouponDTO>> findById(
            @Parameter(description = "ID del cupón a obtener") @PathVariable Integer id) {
        DiscountCouponDTO coupon = discountCouponService.findById(id);
        return ResponseEntity.ok(toEntityModel(coupon));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Obtener cupón por código", description = "Retorna un cupón específico por su código")
    public ResponseEntity<EntityModel<DiscountCouponDTO>> findByCode(
            @Parameter(description = "Código del cupón a buscar") @PathVariable String code) {
        DiscountCouponDTO coupon = discountCouponService.findByCode(code);
        return ResponseEntity.ok(toEntityModel(coupon));
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener cupones activos", description = "Retorna todos los cupones activos para la fecha actual")
    public ResponseEntity<CollectionModel<EntityModel<DiscountCouponDTO>>> findActiveForToday() {
        List<EntityModel<DiscountCouponDTO>> activeCoupons = discountCouponService.findActiveForDate(LocalDate.now()).stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<DiscountCouponDTO>> collectionModel = CollectionModel.of(activeCoupons)
                .add(linkTo(methodOn(DiscountCouponController.class).findActiveForToday()).withSelfRel())
                .add(linkTo(methodOn(DiscountCouponController.class).findAll()).withRel("all-coupons"));
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cupón", description = "Crea un nuevo cupón de descuento")
    public ResponseEntity<EntityModel<DiscountCouponDTO>> create(
            @Parameter(description = "Datos del nuevo cupón") @Valid @RequestBody DiscountCouponDTO dto) {
        DiscountCouponDTO createdCoupon = discountCouponService.create(dto);
        return ResponseEntity.status(201).body(toEntityModel(createdCoupon));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cupón", description = "Actualiza un cupón existente")
    public ResponseEntity<EntityModel<DiscountCouponDTO>> update(
            @Parameter(description = "ID del cupón a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos del cupón") @Valid @RequestBody DiscountCouponDTO dto) {
        DiscountCouponDTO updatedCoupon = discountCouponService.update(id, dto);
        return ResponseEntity.ok(toEntityModel(updatedCoupon));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón por su ID")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del cupón a eliminar") @PathVariable Integer id) {
        discountCouponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<DiscountCouponDTO> toEntityModel(DiscountCouponDTO coupon) {
        return EntityModel.of(coupon)
                .add(linkTo(methodOn(DiscountCouponController.class).findById(coupon.getId())).withSelfRel())
                .add(linkTo(methodOn(DiscountCouponController.class).findAll()).withRel("all-coupons"))
                .add(linkTo(methodOn(DiscountCouponController.class).findByCode(coupon.getCode())).withRel("by-code"))
                .add(linkTo(methodOn(DiscountCouponController.class).findActiveForToday()).withRel("active-coupons"));
    }
}
