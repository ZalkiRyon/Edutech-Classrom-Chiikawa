package com.edutech.payments.controller;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.service.DiscountCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity<List<DiscountCouponDTO>> findAll() {
        return ResponseEntity.ok(discountCouponService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cupón por ID", description = "Retorna un cupón específico por su ID")
    public ResponseEntity<DiscountCouponDTO> findById(
            @Parameter(description = "ID del cupón a obtener") @PathVariable Integer id) {
        return ResponseEntity.ok(discountCouponService.findById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Obtener cupón por código", description = "Retorna un cupón específico por su código")
    public ResponseEntity<DiscountCouponDTO> findByCode(
            @Parameter(description = "Código del cupón a buscar") @PathVariable String code) {
        return ResponseEntity.ok(discountCouponService.findByCode(code));
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener cupones activos", description = "Retorna todos los cupones activos para la fecha actual")
    public ResponseEntity<List<DiscountCouponDTO>> findActiveForToday() {
        return ResponseEntity.ok(discountCouponService.findActiveForDate(LocalDate.now()));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cupón", description = "Crea un nuevo cupón de descuento")
    public ResponseEntity<DiscountCouponDTO> create(
            @Parameter(description = "Datos del nuevo cupón") @Valid @RequestBody DiscountCouponDTO dto) {
        return ResponseEntity.ok(discountCouponService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cupón", description = "Actualiza un cupón existente")
    public ResponseEntity<DiscountCouponDTO> update(
            @Parameter(description = "ID del cupón a actualizar") @PathVariable Integer id, 
            @Parameter(description = "Nuevos datos del cupón") @Valid @RequestBody DiscountCouponDTO dto) {
        return ResponseEntity.ok(discountCouponService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón por su ID")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del cupón a eliminar") @PathVariable Integer id) {
        discountCouponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
