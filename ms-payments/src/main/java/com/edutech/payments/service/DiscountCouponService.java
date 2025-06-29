package com.edutech.payments.service;

import com.edutech.common.dto.DiscountCouponDTO;
import com.edutech.payments.entity.DiscountCoupon;
import com.edutech.payments.mapper.DiscountCouponMapper;
import com.edutech.payments.repository.DiscountCouponRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;

@Service
public class DiscountCouponService {

    private final DiscountCouponRepository discountCouponRepository;
    private final DiscountCouponMapper discountCouponMapper;

    public DiscountCouponService(DiscountCouponRepository discountCouponRepository, DiscountCouponMapper discountCouponMapper) {
        this.discountCouponRepository = discountCouponRepository;
        this.discountCouponMapper = discountCouponMapper;
    }

    public List<DiscountCouponDTO> findAll() {
        return discountCouponRepository.findAll().stream().map(discountCouponMapper::toDTO).toList();
    }

    public DiscountCouponDTO findById(Integer id) {
        return discountCouponMapper.toDTO(orThrow(discountCouponRepository.findById(id), "Cupón de descuento"));
    }

    public DiscountCouponDTO findByCode(String code) {
        return discountCouponMapper.toDTO(orThrow(discountCouponRepository.findByCode(code), "Cupón de descuento"));
    }

    public List<DiscountCouponDTO> findActiveForDate(LocalDate date) {
        return discountCouponRepository.findByValidFromLessThanEqualAndValidUntilGreaterThanEqual(date, date)
                .stream().map(discountCouponMapper::toDTO).toList();
    }

    public DiscountCouponDTO create(DiscountCouponDTO dto) {
        // Crear nuevo cupón
        return saveDTO(dto, null);
    }

    public DiscountCouponDTO update(Integer id, DiscountCouponDTO dto) {
        orThrow(discountCouponRepository.findById(id), "Cupón de descuento");
        return saveDTO(dto, id);
    }

    public void delete(Integer id) {
        discountCouponRepository.delete(orThrow(discountCouponRepository.findById(id), "Cupón de descuento"));
    }

    private DiscountCouponDTO saveDTO(DiscountCouponDTO dto, Integer id) {
        DiscountCoupon entity = discountCouponMapper.toEntity(dto);
        if (id != null) entity.setId(id);
        return discountCouponMapper.toDTO(discountCouponRepository.save(entity));
    }
}
